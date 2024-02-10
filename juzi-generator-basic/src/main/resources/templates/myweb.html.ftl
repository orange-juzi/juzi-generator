<!doctype html>
<html>
<head>
    <title>橘子生成器</title>
</head>
<body>
    <h1>欢迎来到橘子生成器</h1>
    <ul>
        <#-- 环渲染导航条-->
        <#list menuItems as item>
            <li><a href="${item.url}">${item.label}</a></li>
        </#list>
    </ul>
        <#--底部版权信息-->
        <footer>
            ${currentYear} 橘子生成器. All right
        </footer>
</body>
</html>