
package pl.edu.wat.skydive.reflection;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Reflection {
    private TypeDescription entityDefinition;
    private TypeDescription requestDefinition;
    private TypeDescription responseDefinition;
    private TypeDescription mapperDefinition;
    private TypePool typePool;
    private ByteBuddy byteBuddy;

    public Reflection() {
        this.typePool = TypePool.Default.ofSystemLoader();
        this.byteBuddy = new ByteBuddy();
        this.entityDefinition = typePool.describe("pl.edu.wat.skydive.entity.Parachute").resolve();
        this.requestDefinition = typePool.describe("pl.edu.wat.skydive.dto.ParachuteRequest").resolve();
        this.responseDefinition = typePool.describe("pl.edu.wat.skydive.dto.ParachuteResponse").resolve();
        this.mapperDefinition = typePool.describe("pl.edu.wat.skydive.mapper.ParachuteMapper").resolve();

    }
    public static void apply(String fieldName, String fieldType) {
        var ref = new Reflection();
        ref.applyEntity(fieldName, fieldType);
        ref.applyRequest(fieldName, fieldType);
        ref.applyResponse(fieldName, fieldType);
        ref.applyParachuteMapper(fieldName);
    }

    private void applyParachuteMapper(String fieldName) {
        ByteBuddy byteBuddy = new ByteBuddy();
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(mapperDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .method(named("fillParachuteRequest"))
                .intercept(MethodCall.invoke(setterParachuteEntity(fieldName))
                        .onArgument(0)
                        .withMethodCall(MethodCall
                                .invoke(getterRequest(fieldName))
                                .onArgument(1)))
                .method(named("fillParachute"))
                .intercept(MethodCall.invoke(setterParachuteResponse(fieldName))
                        .onArgument(0)
                        .withMethodCall(MethodCall
                                .invoke(getterEntity(fieldName))
                                .onArgument(1)));

        try (var unloadedParachute = builder.make()) {
            mapperDefinition =  unloadedParachute.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MethodDescription getterEntity(String fieldName) {
        return entityDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isGetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription setterParachuteResponse(String fieldName) {
        return responseDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isSetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription getterRequest(String fieldName) {
        return requestDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isGetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription setterParachuteEntity(String fieldName) {
        return entityDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isSetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }


    private void applyResponse(String fieldName, String fieldType) {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(responseDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(fieldName, typePool.describe(fieldType).resolve());

        try (var unloadedParachute = builder.make()) {
            responseDefinition = unloadedParachute.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void applyRequest(String fieldName, String fieldType) {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(requestDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(fieldName, typePool.describe(fieldType).resolve());

        try (var unloadedParachute = builder.make()) {
            requestDefinition = unloadedParachute.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void applyEntity(String fieldName, String fieldType) {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(entityDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(fieldName, typePool.describe(fieldType).resolve());

        try (var unloadedParachute = builder.make()) {
            entityDefinition = unloadedParachute.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
