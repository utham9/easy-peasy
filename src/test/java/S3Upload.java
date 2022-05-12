import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Builder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class S3Upload {

  @Test
  public void upload() {
    AWSCredentials credentials = new BasicAWSCredentials("", "");
    AmazonS3 amazonS3 =
        AmazonS3ClientBuilder
            .standard() // .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_2)
            .build();
    List<Bucket> buckets = amazonS3.listBuckets();
    String bucketName = "testuthamtobedeleted";
    if (!buckets.stream().anyMatch(b -> b.getName().equals(bucketName))) {
      amazonS3.createBucket("testuthamtobedeleted");
    } else {
        amazonS3.deleteBucket(bucketName);
    }
    //amazonS3.putObject("testuthamtobedeleted", "Hello/sd.txt", "wow! it works");
  }
}
