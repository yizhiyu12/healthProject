package MyTest;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;


public class QiNiuTest {
    public static void main(String[] args) {

        //"http://"+rghoo8ubg.hn-bkt.clouddn.com+"/"+getImageName;
        //http://rghoo8ubg.hn-bkt.clouddn.com/abc.jpg
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //...生成上传凭证，然后准备上传
        String accessKey = "j3aDbgGrT-Gf30kBBIT_63vPZ0e_Wz2fhWc6sgMq";
        String secretKey = "R9IR6H9lpdczDeBem1nAVYd4PVlTGhBiuxaFiFCM";
        String bucket = "lili-123";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "/Users/lihongyuan/Documents/health_imgs/WechatIMG267.jpeg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key ="gong.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }


    //删除七牛云服务器中的图片
//    public static void main(String[] args) {
//        //构造一个带指定Zone对象的配置类
//        Configuration cfg = new Configuration(Zone.zone0());
//        //...其他参数参考类注释
//        String accessKey = "j3aDbgGrT-Gf30kBBIT_63vPZ0e_Wz2fhWc6sgMq";
//        String secretKey = "R9IR6H9lpdczDeBem1nAVYd4PVlTGhBiuxaFiFCM";
//        String bucket = "lili-123";
//        String key = "123.jpg";
//        Auth auth = Auth.create(accessKey, secretKey);
//        BucketManager bucketManager = new BucketManager(auth, cfg);
//        try {
//            bucketManager.delete(bucket, key);
//        } catch (QiniuException ex) {
//            //如果遇到异常，说明删除失败
//            System.err.println(ex.code());
//            System.err.println(ex.response.toString());
//        }
//    }



}
