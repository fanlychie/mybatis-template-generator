import org.fanlychie.mybatis.template.Generator;

/**
 * MyBatis 模板生成器
 * Created by fanlychie on 2017/2/2.
 */
public class MyBatisTemplateGenerator {

    /**
     * 引入 mybatis-template-generator 依赖, scope 设置 test, 以确保打包时可顺利排除此依赖.
     * 执行 maven 命令生成模板代码文件:
     * mvn exec:java -Dexec.mainClass=MyBatisTemplateGenerator -Dexec.classpathScope=test
     */
    public static void main(String[] args) {
        // 生成模板
        Generator.generate();
        /**
         * eclipse 正常.
         * intellij idea 关闭不了线程, 未找到原因! 特加此行以正常退出.
         */
        System.exit(0);
    }

}