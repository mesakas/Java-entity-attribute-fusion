
// 属性融合(和lombok配合使用)
public class AttributeFusion {

    // 将交集的内容合并(不支持布尔类型)，sets会覆盖target中的数据，支持多个集合同时插入target，但是多个集合之间不能有相同的字段
    public static Object intersectionAssign(Object target, Object... sets) {

        // 获取target的所有field名
        LinkedList<String> targetFieldSetterMethodsNoPrefixNameList = new LinkedList<>();
        for (Method targetMethod : target.getClass().getMethods()) {
            String targetMethodName = targetMethod.getName();
            if (targetMethodName.equals("equals") ||
                    targetMethodName.equals("toString") ||
                    targetMethodName.equals("hashCode") ||
                    targetMethodName.equals("wait") ||
                    targetMethodName.equals("getClass") ||
                    targetMethodName.equals("notify") ||
                    targetMethodName.equals("notifyAll") ||
                    targetMethodName.startsWith("set")) continue;
            targetFieldSetterMethodsNoPrefixNameList.add(targetMethod.getName().substring(3));
        }

        // 匹配同名方法，并将同名方法赋值给target
        for (Object set : sets) {
            for (Method method : set.getClass().getMethods()) {
                String name = method.getName();
                if (name.equals("equals") ||
                        name.equals("toString") ||
                        name.equals("hashCode") ||
                        name.equals("wait") ||
                        name.equals("getClass") ||
                        name.equals("notify") ||
                        name.equals("notifyAll") ||
                        name.startsWith("get")) continue;

                String substringName = name.substring(3);
                if (targetFieldSetterMethodsNoPrefixNameList.contains(substringName)) {

                    try {
                        Object value = set.getClass().getMethod("get" + substringName).invoke(set);
                        target.getClass().getMethod("set" + substringName, method.getParameterTypes()[0]).invoke(target, value);
                    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return target;
    }
}
