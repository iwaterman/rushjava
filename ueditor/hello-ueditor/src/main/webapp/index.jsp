<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <script type="text/javascript">
        var basePath = "<%=path %>"
        window.UEDITOR_HOME_URL = "<%=path %>/ueditor/"
    </script>
    <title>编辑器完整版实例</title>

    <link rel="stylesheet" type="text/css" href="ueditor/themes/iframe.css"/>
</head>

<body>
<div id="myEditor">
</div>
<div>
    <input type="button" onclick="postUEData()" value="提交"/>
</div>

<script type="text/javascript" src="ueditor/third-party/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="ueditor/ueditor.all.js"></script>

<script type="text/javascript">
    function postUEData() {
        var data = editor.getContent();
        var obj = {};

        obj.data = data;

        $.ajax({
            type: "POST",
            url: "content.ue",
            data: obj,
            success: function(msg){
                alert( "Data Saved: " + msg );
            }
        });
    }

    $(function(){
        window.editor = new baidu.editor.ui.Editor({toolbars: [
            [
                'fullscreen', 'source', '|', 'undo', 'redo', '|',
                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                'print', 'preview', 'searchreplace', 'help', 'drafts'
            ]
        ]});
        editor.render("myEditor");


    })
</script>
</body>
</html>
