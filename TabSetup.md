# Tab Setup #
## NetBeans ##
  * http://code.google.com/p/amity-apcs-project-x-2011/downloads/detail?name=NetBeansFormatting.zip
  * Tools>Options>Import>select the zip file.
  * Tools>Templates>Java>Java class>Open in editor
  * Update it to the following:
```
<#assign licenseFirst = "/*">
<#assign licensePrefix = " * ">
<#assign licenseLast = " */">
/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
 *
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation.
 */
<#if package?? && package != "">
package ${package};

</#if>
/**
 * Class documentation.
 *
 * @author ${user}
 */
public class ${name} {

}

```

## Eclipse ##
  * http://code.google.com/p/amity-apcs-project-x-2011/downloads/detail?name=EclipseFormatting.xml
  * Window>Preferences>Java>Code Style>Formatter>Import...>select the xml file.
  * Window>Preferences>Java>Code Style>Code Templates>Code>New Java Files>Edit...
  * Change to:
```
${filecomment}
/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
 *
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation.
 */
${package_declaration}

${typecomment}
${type_declaration}
```

## Command Line ##
  * If you use a modern `vi`, just make sure you `:set modeline` in your `.exrc`. The source files have appropriate modelines.
  * No spaces before function parentheses.
  * Use comment and brace style as in `trunk/src/org/amityregion5/Template.java`.
    * The newline after the `class` declaration is optional and harmless.
  * Use a 4-space tab stop for indentation.
  * Use template located at org.amityregion5.Template