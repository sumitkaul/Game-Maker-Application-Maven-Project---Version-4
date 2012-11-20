<?php 

   $app_id = "523087041036918";
   $app_secret = "1cf5f653185bb11322727edfbaf81c29";
   $my_url = "https://www.facebook.com/appcenter/teamall";

   session_start();
   
   $code = $_REQUEST["code"];

   if(empty($code)) {
     $_SESSION['state'] = md5(uniqid(rand(), TRUE)); // CSRF protection
     $dialog_url = "https://www.facebook.com/dialog/oauth?client_id=" 
       . $app_id . "&redirect_uri=" . urlencode($my_url) . "&state="
       . $_SESSION['state'];

     echo("<script> top.location.href='" . $dialog_url . "'</script>");
   }
   
   if($_SESSION['state'] && ($_SESSION['state'] === $_REQUEST['state'])) {
     echo("<script> top.location.href='" . urlencode($my_url) . "state="
       . $_SESSION['state'] . "&code=" . $code . "'</script>");
   }
   else {
     echo("The state does not match. You may be a victim of CSRF.");
   }
   
 ?>
