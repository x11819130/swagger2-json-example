package com.test.swagger;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Optional;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ApiJsonParameterBuilder
 * <p>
 * 将map入参匹配到swagger文档的工具类
 * plugin加载顺序，默认是最后加载
 *
 * @author chengz
 * @date 2020/10/14
 */
@Component
@Order
public class ApiSimpleModelBuilder implements ParameterBuilderPlugin {
    private static final Logger logger = LoggerFactory.getLogger(ApiSimpleModelBuilder.class);
    @Autowired
    private TypeResolver typeResolver;
    /**
     * 动态生成的Class的包路径
     */
    private final static String BASE_PACKAGE = "com.platform.entity.";
    /**
     * 默认类名
     */
    private final static String DEFAULT_CLASS_NAME = "RequestInfo";
    /**
     * 序号 防止重名
     */
    private static Integer i = 0;

    @Override
    public void apply(ParameterContext context) {
        try {
            // 从方法或参数上获取指定注解的Optional
            Optional<ApiSimpleModel> optional = context.getOperationContext().findAnnotation(ApiSimpleModel.class);
            if (!optional.isPresent()) {
                optional = context.resolvedMethodParameter().findAnnotation(ApiSimpleModel.class);
            }
            if (optional.isPresent()) {
                String key = DEFAULT_CLASS_NAME + i++;
                ApiSimpleModel apiAnno = optional.get();
                try {
                    //类名重复将导致swagger识别不准确 主动触发异常
                    Class.forName(BASE_PACKAGE + key);
                } catch (ClassNotFoundException e) {
                    String[] fields = apiAnno.value();
                    String separator = apiAnno.separator();
                    List<String> fieldList = merge(fields, separator);
                    Class<?> ctClass = createRefModel(fieldList, key);
                    // 将生成的Class添加到SwaggerModels
                    context.getDocumentationContext().getAdditionalModels()
                            .add(typeResolver.resolve(ctClass));
                    // 修改Json参数的ModelRef为动态生成的class
                    context.parameterBuilder()
                            .parameterType("body").modelRef(new ModelRef(key)).name("body").description("body");
                }
            }
        } catch (Exception e) {
            logger.error("@ApiSimpleModel Error", e);
        }
    }

    /**
     * 字符串列表 分隔符 合并
     * A{"a","b,c","d"} => B{"a","d","b","c"}
     *
     * @param arr arr
     * @return list
     */
    private List<String> merge(String[] arr, String separator) {
        List<String> tmp = new ArrayList<>();
        Arrays.stream(arr).forEach(s -> {
            if (s.contains(separator)) {
                tmp.addAll(Arrays.asList(s.split(separator)));
            } else {
                tmp.add(s);
            }
        });
        return tmp;
    }

    /**
     * MapReaderForApi
     * 根据propertys中的值动态生成含有Swagger注解的javaBeen
     *
     * @author chengz
     * @date 2020/10/14
     */
    private Class<?> createRefModel(List<String> fieldList, String key) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(BASE_PACKAGE + key);
        ctClass.setModifiers(Modifier.ABSTRACT);
        for (String field : fieldList) {
            CtField newCtField = createField(field, ctClass);
            ctClass.addField(newCtField);
        }
        return ctClass.toClass();
    }

    /**
     * 根据property的值生成含有swagger apiModelProperty注解的属性
     */
    private CtField createField(String field, CtClass ctClass) throws Exception {
        CtField ctField = new CtField(ClassPool.getDefault().get(String.class.getName()), field, ctClass);
        ctField.setModifiers(Modifier.PUBLIC);
        return ctField;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}