package rookie.midl.codegen;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * the render to generate target code
 * @author Xuanchen Chen
 * @version 1.0.0
 * @date 2023.6.6
 * */
public class CodeRender {

    private VarCollector varCollector;

    private STGroup stg;

    private String filename;

    public CodeRender(VarCollector varCollector, String groupFile, String filename) {
        this.varCollector = varCollector;
        this.stg = new STGroupFile(groupFile);
        this.filename = filename;
        preProcess();
    }

    /**
     * render all the variables to target code
     * @param target the file to save target code
     * */
    public void render(String target) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(stg.getInstanceOf("file_head").add("filename", filename).render());
        for (String type : varCollector) {
            List<Variable> variables = varCollector.getVariables(type);
            builder.append(render(type, variables));
        }

        // end
        builder.append(stg.getInstanceOf("file_end").render());
        Files.writeString(Path.of(target), builder.toString());
        System.out.println(builder);
    }

    private String render(String name, List<Variable> variables) {
        StringBuilder builder = new StringBuilder();
        String preName = name;
        name = name.replaceAll("::", "_");
        // rend head
        String head = stg.getInstanceOf("obj_head")
                .add("name", name)
                .render();
        builder.append(head);

        // body
        String bodyStart = stg.getInstanceOf("obj_body")
                .add("name", name).render();
        builder.append(bodyStart);

        for (Variable variable : variables) {
            ST genDecl = genST("gen_decl", variable, true);
            builder.append(genDecl.render());
        }

        builder.append(stg.getInstanceOf("decl_end").add("name", name).render());

        builder.append(stg.getInstanceOf("read_method").add("name", name).render());

        // constructor. if the structure is nested
        if (varCollector.isNested(preName)) {
            builder.append(stg.getInstanceOf("nested_constructor_start").add("name", name).render());
            for (Variable variable : varCollector.getVariables(preName)) {
                if (!variable.isPrimitive()) {
                    genStructureInitParam(variable.getType(), variable.getName(), builder);
                } else {
                    ST st = stg.getInstanceOf("nested_constructor_param");
                    st.add("nested", false);
                    st.add("outerdecl", variable.getName());
                    st.add("expr", variable.getExpr());
                    builder.append(st.render());
                }
            }

            builder.append(stg.getInstanceOf("method_end").render());
        }

        // init
        builder.append(stg.getInstanceOf("init_method_start").add("name", name).render());

        for (Variable variable : variables) {
            ST initParam = genST("init_param", variable, false);
            builder.append(initParam.render());
        }

        builder.append(stg.getInstanceOf("method_end").render());

        builder.append(stg.getInstanceOf("finalize_start").add("type", name).render());

        for (Variable variable : variables) {
            ST finalizeParam = genST("finalize_param", variable, false);
            builder.append(finalizeParam.render());
        }

        builder.append(stg.getInstanceOf("method_end").render());

        return builder.toString();
    }

    /**
     * convert the first letter to uppercase if it is lowercase
     * @param str the string to be converted
     * */
    private static String firstLetterToUppercase(String str) {
        char[] chars = str.toCharArray();
        if (chars[0] >= 97 && chars[0] <= 122)
            chars[0] -= 32;
        return new String(chars);
    }

    /**
     * process the variable type before render
     * */
    private void preProcess() {
        for (String typeName : varCollector) {
            List<Variable> variables = varCollector.getVariables(typeName);
            for (Variable variable : variables) {
                String type = variable.getType();
                if (variable.isPrimitive())
                    variable.setType(convertTypeName(type));
                variable.setType(variable.getType().replaceAll("::", "_"));
            }
        }
    }

    private String convertTypeName(String type) {
        switch (type) {
            case "short":
            case "boolean":
            case "long":
            case "float":
            case "double":
                return firstLetterToUppercase(type);
            case "int8":
            case "int16":
                return "Short";
            case "int32":
                return "Long";
            case "int64":
                return "LongLong";
            case "uint8":
            case "uint16":
                return "UnsignedShort";
            case "uint32":
                return "UnsignedLong";
            case "uint64":
                return "UnsignedLongLong";
        }
        if (type.contains(" ")) {
            String[] s = type.split(" ");
            StringBuilder ret = new StringBuilder();
            for (String s1 : s) {
                ret.append(firstLetterToUppercase(s1));
            }
            return ret.toString();
        }
        return type;
    }

    private ST genST(String instanceName, Variable variable, boolean exprNeeded) {
        ST st = stg.getInstanceOf(instanceName);
        st.add("type", variable.getType());
        st.add("decl", variable.getName());
        if (exprNeeded)
            st.add("expr", variable.getExpr());
        st.add("isPrimitive", variable.isPrimitive());
        st.add("isArr", variable.isArr());
        st.add("arrSize", variable.getArrSize());
        return st;
    }

    /**
     * get the param need to be initialized in the structure
     * @param structName the qualified name of that structure
     * @param decl the variable name of that structure
     * @param builder the string builder for saving information
     * */
    private void genStructureInitParam(String structName, String decl, StringBuilder builder) {
        String name = structName.replaceAll("_", "::");
        List<Variable> variables = varCollector.getVariables(name);
        ST nested = stg.getInstanceOf("nested_constructor_param");
        for (Variable variable : variables) {
            if (variable.isPrimitive()) {
                nested.add("nested", true);
                nested.add("outerdecl", decl);
                nested.add("innerdecl", variable.getName());
                nested.add("expr", variable.getExpr());
                builder.append(nested.render());
            } else {
                genStructureInitParam(variable.getType(), decl + variable.getName(), builder);
            }
        }
    }
}
