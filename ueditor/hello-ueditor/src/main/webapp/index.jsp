<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript">
    var basePath="<%=path %>"
    window.UEDITOR_HOME_URL="<%=path %>/ueditor/"
    </script>
   <title>编辑器完整版实例</title>
	<script type="text/javascript" src="ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="ueditor/ueditor.all.js"></script>
	<link rel="stylesheet" type="text/css" href="ueditor/themes/iframe.css"/> 
  </head>
  
  <body>
   <div id="myEditor">
	 	<script type="text/javascript" charset="utf-8">
			var editor = new baidu.editor.ui.Editor({toolbars: [[
                'fullscreen', 'source', '|', 'undo', 'redo', '|',
                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                'print', 'preview', 'searchreplace', 'help', 'drafts'
            ]]});
			editor.render("myEditor");
		</script>
	</div>
  </body>
</html>
