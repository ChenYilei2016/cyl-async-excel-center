package cn.chenyilei.aec.infrastructure.oss;

import cn.chenyilei.aec.domain.oss.AecOssService;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.OSSObject;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Map;

/**
 * https://help.aliyun.com/zh/oss/developer-reference/java-installation?spm=a2c4g.11186623.0.0.6e71539bMeBC7Z
 *
 * @author chenyilei
 * @date 2023/09/26 15:03
 */
public class AecAliyunOssServiceImpl implements AecOssService {

    private String bucketName;

    private OSSClient ossClient;

    public AecAliyunOssServiceImpl(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        DefaultCredentialProvider defaultCredentialProvider = CredentialsProviderFactory.newDefaultCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration config = new ClientConfiguration();
        this.ossClient = new OSSClient(endpoint, defaultCredentialProvider, config);
        this.bucketName = bucketName;
    }

    @Override
    public void upload(String path, InputStream inputStream, Map<String, Object> runtimeParams) {
        String bucketName = (String) runtimeParams.get(AecOssConfigProperties.BUCKET_NAME_KEY);
        if (StringUtils.isBlank(bucketName)) {
            ossClient.putObject(this.bucketName, path, inputStream);
        } else {
            ossClient.putObject(bucketName, path, inputStream);
        }
    }

    @Override
    public InputStream download(String path, Map<String, Object> runtimeParams) {
        String bucketName = (String) runtimeParams.get(AecOssConfigProperties.BUCKET_NAME_KEY);
        OSSObject object;
        if (StringUtils.isBlank(bucketName)) {
            object = ossClient.getObject(this.bucketName, path);
        } else {
            object = ossClient.getObject(bucketName, path);
        }
        if (object == null) {
            return null;
        }
        return object.getObjectContent();
    }

    @Override
    public void remove(String path, Map<String, Object> runtimeParams) {
        String bucketName = (String) runtimeParams.get(AecOssConfigProperties.BUCKET_NAME_KEY);
        if (StringUtils.isBlank(bucketName)) {
            ossClient.deleteObject(this.bucketName, path);
        } else {
            ossClient.deleteObject(bucketName, path);
        }
    }

    @Override
    public boolean exists(String path, Map<String, Object> runtimeParams) {
        String bucketName = (String) runtimeParams.get(AecOssConfigProperties.BUCKET_NAME_KEY);
        if (StringUtils.isBlank(bucketName)) {
            return ossClient.doesObjectExist(this.bucketName, path);
        } else {
            return ossClient.doesObjectExist(bucketName, path);
        }
    }
}
