/*
 * Copyright 2008 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mybatis.generator.internal;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

import java.util.Date;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;

/**
 * @author Jeff Butler
 * 
 */
public class DefaultCommentGenerator implements CommentGenerator {

    private Properties properties;
    private boolean suppressDate;
    private boolean suppressAllComments;

    public DefaultCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
    }

    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments by default
        return;
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and when it was generated.
     */
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }

        xmlElement.addElement(new TextElement("<!--")); //$NON-NLS-1$
        xmlElement.addElement(new TextElement(("    警告 - " + MergeConstants.NEW_ELEMENT_TAG))); //$NON-NLS-1$
        xmlElement.addElement(new TextElement("    由MyBatis Generator自动生成的代码，请不要删除。")); //$NON-NLS-1$

        String s = getDateString();
        if (s != null) {
            xmlElement.addElement(new TextElement("  生成时间：" + s));
        }

        xmlElement.addElement(new TextElement("-->")); //$NON-NLS-1$
    }

    public void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
        return;
    }

    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do
     * not wish to include the Javadoc tag - however, if you do not include the
     * Javadoc tag then the Java merge capability of the eclipse plugin will
     * break.
     * 
     * @param javaElement
     *            the java element
     */
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ').append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * This method returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     * 
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else {
            return new Date().toString();
        }
    }

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        addClassComment(innerClass, introspectedTable, false);
    }

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }

        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        innerClass.addJavaDocLine(" * 该类由MyBatis Generator生成。"); //$NON-NLS-1$
        innerClass.addJavaDocLine(" * 该类对应数据库表： " + introspectedTable.getFullyQualifiedTable() + " - " //$NON-NLS-1$
                + introspectedTable.getTableConfiguration().getTableRemark());
        addJavadocTag(innerClass, markAsDoNotDelete);
        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        innerEnum.addJavaDocLine("/**"); //$NON-NLS-1$
        innerEnum.addJavaDocLine(" * 该枚举由MyBatis Generator生成。"); //$NON-NLS-1$
        innerEnum.addJavaDocLine(" * 该枚举对应数据库表： " + introspectedTable.getFullyQualifiedTable()); //$NON-NLS-1$
        addJavadocTag(innerEnum, false);
        innerEnum.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        field.addJavaDocLine("/**"); //$NON-NLS-1$
        field.addJavaDocLine(" * 该字段由MyBatis Generator生成。"); //$NON-NLS-1$
        field.addJavaDocLine(" * 该字段对应数据库字段： " + introspectedTable.getFullyQualifiedTable() + "."
                + introspectedColumn.getActualColumnName() + " - " + introspectedColumn.getRemarks()); // $NON-NLS-1$
        addJavadocTag(field, false);
        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        field.addJavaDocLine("/**"); //$NON-NLS-1$
        field.addJavaDocLine(" * 该字段由MyBatis Generator生成。"); //$NON-NLS-1$
        // field.addJavaDocLine(" * 该字段对应数据库表： " + introspectedTable.getFullyQualifiedTable()); //$NON-NLS-1$
        addJavadocTag(field, false);
        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**"); //$NON-NLS-1$
        method.addJavaDocLine(" * 该方法由MyBatis Generator生成。"); //$NON-NLS-1$
        // method.addJavaDocLine(" * 该方法对应数据库表： " + introspectedTable.getFullyQualifiedTable()); //$NON-NLS-1$
        addJavadocTag(method, false);
        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**"); //$NON-NLS-1$
        method.addJavaDocLine(" * 该方法由MyBatis Generator生成。"); //$NON-NLS-1$
        method.addJavaDocLine(" * 该方法返回数据库字段： " + introspectedTable.getFullyQualifiedTable() + "."
                + introspectedColumn.getActualColumnName()); // $NON-NLS-1$
        method.addJavaDocLine(" *"); //$NON-NLS-1$
        method.addJavaDocLine(" * @return " + introspectedTable.getFullyQualifiedTable() + "."
                + introspectedColumn.getActualColumnName() + " - " + introspectedColumn.getRemarks()); // $NON-NLS-1$
        addJavadocTag(method, false);
        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        Parameter parm = method.getParameters().get(0);
        method.addJavaDocLine("/**"); //$NON-NLS-1$
        method.addJavaDocLine(" * 该方法由MyBatis Generator生成。"); //$NON-NLS-1$
        method.addJavaDocLine(" * 该方法设置数据库字段： " + introspectedTable.getFullyQualifiedTable() + "."
                + introspectedColumn.getActualColumnName()); // $NON-NLS-1$
        method.addJavaDocLine(" *"); //$NON-NLS-1$
        method.addJavaDocLine(" * @param " + parm.getName() + " " + introspectedTable.getFullyQualifiedTable() + "."
                + introspectedColumn.getActualColumnName() + " - " + introspectedColumn.getRemarks()); // $NON-NLS-1$
        addJavadocTag(method, false);
        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }
}
