package finn;

import com.google.common.primitives.Chars;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

/**
 * Hello world!
 */
public class Application {

    public static void main(String[] args) {

        // SparkConf sparkConf = new SparkConf().setAppName("wordCount");
        SparkConf sparkConf = new SparkConf().setAppName("wordCount").setMaster("local");

        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);

        final int threshold = Integer.parseInt(args[1]);

        JavaRDD<String> tokenized = javaSparkContext.textFile(args[0])
                .flatMap(s -> Arrays.asList(s.split(" ")).iterator());

        JavaPairRDD<String, Integer> counts = tokenized
                .mapToPair(s -> new Tuple2<>(s, 1))
                .reduceByKey((c1, c2) -> c1 + c2);

        JavaPairRDD<String, Integer> filtered = counts.filter(t -> t._2 >= threshold);

        JavaRDD<Character> charTokenized = filtered.flatMap(t -> Chars.asList(t._1.toCharArray()).iterator());

        JavaPairRDD<Character, Integer> charCounts = charTokenized
                .mapToPair(c -> new Tuple2<>(c, 1))
                .reduceByKey((c1, c2) -> c1 + c2);

        System.out.println(charCounts.collect());

    }
}
