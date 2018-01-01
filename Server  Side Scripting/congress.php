<!-- DESCRIPTION 

In this Project, a webpage is created that allows you to search for congress senator information using the Sunlight Congress APIs, and the results will be displayed in a tabular format.

A user first opens a page, called congress.php, where he/she can
select a congress database, choose chamber type (i.e., Senate or House) and enter a keyword.

Initially a user needs to select the options for Congress Database:

The congress database can be one of the following options: Legislators, Committees, Bills, and Amendments.
For each option, when selected, the text “Keyword*” is replaced with the respective text:
For Legislators: “State/Representative*”
For Committees: “Committee ID*”
For Bills: “Bill ID*”
For Amendments: “Amendment ID*”

Search button:
If the user clicks on the Search button without providing a value for all of the form fields, an error message is displayed indicating the missing field/fields.

Once the user provides all selections and required data and clicks on the Search button, the page sends a request to your web server for congress.php with the form data. We use either GET or POST to transfer the form data to the web server. A PHP script will retrieve the data and use it to query the Sunlight Congress API Service.

Clear button: This button must clear the result area and reset the form fields to their initial state (including the text “Keywords*”). The Clear operation is done using a JavaScript function.

<?php 

    $tbox="";
    $APIkey="b2aff0a1fbed4ac08b2daefb56f7c9a4";

    //array used to map state names to an abbreviated 2 letter state code
    $State_Code=array(
                'ALABAMA' => 'AL', 'MONTANA' => 'MT', 'ALASKA' => 'AK', 'NEBRASKA' => 'NE', 'ARIZONA' => 'AZ', 'NEVADA' => 'NV', 
                'ARKANSAS' => 'AR', 'NEW HAMPSHIRE' => 'NH','CALIFORNIA' => 'CA', 'NEW JERSEY' => 'NJ', 'COLORADO' => 'CO', 'NEW MEXICO' => 'NM', 
                'CONNECTICUT' => 'CT', 'NEW YORK' => 'NY','DELAWARE' => 'DE', 'NORTH CAROLINA' => 'NC', 'DISTRICT OF COLUMBIA' => 'DC', 'NORTH DAKOTA' => 'ND', 'FLORIDA' => 'FL', 'OHIO' => 'OH', 'GEORGIA' => 'GA', 'OKLAHOMA' => 'OK', 'HAWAII' => 'HI', 'OREGON' => 'OR', 
                'IDAHO' => 'ID', 'PENNSYLVANIA' => 'PA', 'ILLINOIS' => 'IL', 'RHODE ISLAND' => 'RI', 'INDIANA' => 'IN', 'SOUTH CAROLINA' => 'SC', 
                'IOWA' => 'IA', 'SOUTH DAKOTA' => 'SD', 'KANSAS' => 'KS', 'TENNESSEE' => 'TN', 'KENTUCKY' => 'KY', 'TEXAS' => 'TX', 'LOUISIANA' => 'LA', 'UTAH' => 'UT','MAINE' => 'ME', 'VERMONT' => 'VT', 'MARYLAND' => 'MD', 'VIRGINIA' => 'VA', 'MASSACHUSETTS' => 'MA', 'WASHINGTON' => 'WA', 'MICHIGAN' => 'MI', 'WEST VIRGINIA' => 'WV', 'MINNESOTA' => 'MN', 'WISCONSIN' => 'WI', 'MISSISSIPPI' => 'MS', 'WYOMING' => 'WY', 'MISSOURI' => 'MO'   );

    error_reporting(E_ALL ^ E_NOTICE);
    error_reporting(E_ERROR | E_PARSE);
    ini_set('session.cache_limiter','public');
    session_cache_limiter(false);

    //start the session
    session_start();
    

    if ((!empty($_POST["keyword"]))&&(!empty($_POST["Chamber"]))&&(!empty($_POST["choice"])))
    {     
        //Session variable used to retain form values
        $_SESSION['x']=0;
        $_SESSION['y1']=0;
        $_SESSION['y2']=0;
        $_SESSION['z']=0;
        $_SESSION['opt1']=0;
        $_SESSION['opt2']=0;
    
        $ch=$_POST["choice"]; 
        
        $keyword=$_POST["keyword"]; 
        $tbox=$keyword;
        
        $chamber=$_POST["Chamber"];  
        $cham=$chamber;
    
        if($_SERVER["REQUEST_METHOD"] == "POST")
        {
            $_SESSION['dropdown']=$ch;
            $_SESSION['rad1']=$cham;
            $_SESSION['tbox1']=$tbox;
            
            
            //filtering the data based on Legislators
            if($ch=='L')
            {       
                if(strlen($State_Code[strtoupper($keyword)])!=0){
                    
                    if(strlen($keyword)>2)
                    $keyword=$State_Code[strtoupper($keyword)];
               
                    $url='http://congress.api.sunlightfoundation.com/legislators?chamber='.$chamber.'&state='.$keyword.'&apikey='.$APIkey;
                }
                
                else if(strlen($keyword)==2){
               
                    $output = "<center><b>The API returned zero results for the request<b><center>";
                }
               
                else {
               
                       $keyword=strtolower($keyword);
                       $keyword1 = explode(" ", $keyword);
                       if(strlen($keyword1[1])==0)
                           $keyword=$keyword1[0];
                       else
                           $keyword=$keyword1[1];
                    
                    $url = 'http://congress.api.sunlightfoundation.com/legislators?chamber='.$chamber.'&query='.$keyword.'&apikey='.$APIkey;
                }
               
                //get json data
               $solution=file_get_contents($url);
               $object=json_decode($solution,true);
            
               //check for empty request
               if($object['results'][0]=="")
               {  
                   $output = "<center><b>The API returned zero results for the request<b><center>";
                    //$_SESSION['x']=2;
               }
           
               else
               {
                   $output = "";

                   $output .="<center><table border=1 cellpadding=\"5%\" style=\"border-collapse:collapse\"><tr><th>Name</th><th>State</th><th>Chamber</th><th>Details</th></tr>";

                   foreach($object['results'] as $res)
                   {
                        $id=$res['bioguide_id'];

                        $url_details="<a href=\"congress.php?R3=".$id."&R4=".$res['state']."\">View Details</a>";

                        $output .= "<tr><td width=200px>".$res['first_name']." ".$res['last_name']."</td><td width=200px>".$res['state_name']."</td><td width=200px>".$res['chamber']."</td><td width=200px>".$url_details."</td>";   
                    }

                    $output .="</table></center>";
                }   
            }// end of legislators
       
            
            //filtering the data based on committees
            if($ch=='C')
            {
                $_SESSION['x']=1;
                $_SESSION['y2']=2;

                //https://congress.api.sunlightfoundation.com/committees?committee_id=COMMITTEE_ID_HERE&chamber=CHAMBER_TYPE_HERE&apikey=YOUR_API_KEY_HERE
                $url="http://congress.api.sunlightfoundation.com/committees?committee_id=".$keyword."&chamber=".$chamber."&apikey=".$APIkey;

                $solution=file_get_contents($url);
                $object=json_decode($solution,true);

                if($object['results'][0]=="")
                    $output = "<center><b>The API returned zero results for the request<b><center>";
                else
                {
                    $output="";
                    $output .="<center><table border=1 cellpadding=\"5%\" style=\"border-collapse:collapse\"><tr><th>Committee ID</th><th>Committee Name</th><th>Chamber</th></tr>";

                    foreach($object['results'] as $res)
                    {
                       $output .= "<tr><td width=200px>".$res['committee_id']."</td><td width=500px>".$res['name']."</td><td width=200px>".$res['chamber']."</td>";
                    }

                    $output .="</table></center>";

                }  
             }// end of Committee
       
             //filtering the data based on amendments
             if($ch=='A')
             {
                 $_SESSION['x']=1;
                 $_SESSION['y2']=2;

                //https://congress.api.sunlightfoundation.com/amendments?amendment_id=AMENDMENT_ID_HERE&chamber=CHAMBER_TYPE_HERE&apikey=YOUR_API_KEY_HERE
                $url="http://congress.api.sunlightfoundation.com/amendments?amendment_id=".$keyword."&chamber=".$chamber."&apikey=".$APIkey;

                $solution=file_get_contents($url);
                $object=json_decode($solution,true);

                if($object['results'][0]=="")
                    $output = "<center><b>The API returned zero results for the request<b><center>";

                else
                {
                    $output="";
                    $output .="<center><table border=1 cellpadding=\"5%\" style=\"border-collapse:collapse\"><tr><th>Amendment ID</th><th>Amendment Type</th><th>Chamber</th><th>Introduced on</th></tr>";

                    foreach($object['results'] as $res)
                    {
                        $output .= "<tr><td width=200px>".$res['amendment_id']."</td><td width=200px>".$res['amendment_type']."</td><td width=200px>".$res['chamber']."</td><td>".$res['introduced_on']."</td>"; 
                    }

                    $output .="</table></center>";

                }  

             }
            // end of amendment
            
                
            //Filtering the data based on bills 
               if($ch=='B')
               {

                 // https://congress.api.sunlightfoundation.com/bills?bill_id=BILL_ID_HERE&chamber=CHAMBER_TYPE_HERE&apikey=YOUR_API_KEY_HERE
                    $url="http://congress.api.sunlightfoundation.com/bills?bill_id=".$keyword."&chamber=".$chamber."&apikey=".$APIkey;

                    $solution=file_get_contents($url);
                    $object=json_decode($solution,true);

                    if($object['results'][0]=="")
                        $output = "<center><b>The API returned zero results for the request<b><center>";
                    else
                    {
                        $output="";

                        $output .="<center><table border=1 cellpadding=\"5%\" style=\"border-collapse:collapse\"><tr><th>Bill ID</th><th>Short Title</th><th>Chamber</th><th>Details</th></tr>";
                        foreach($object['results'] as $res)
                        {
                            $id=$res['bill_id'];

                            $url_details="<a href=\"congress.php?R1=".$id."&R2=".$res['chamber']."\">View Details</a>";

                            $output .= "<tr><td width=200px>".$res['bill_id']."</td><td width=200px>".$res['short_title']."</td><td width=200px>".$res['chamber']."</td><td width=200px>".$url_details."</td>";

                        }

                        $output .="</table></center>";

                    }
                }
     // end of Bills               
        }
    }

    else 
    {
        $res1 = $_GET['R1']; 
        $res2 = $_GET['R2']; 
        $rad=$_SESSION['rad1'];
        $tbox=$_SESSION['tbox1']; 
        if($res1==$tbox) // bill id hence bill
        {
            
            $url_new = "http://congress.api.sunlightfoundation.com/bills?bill_id=".$res1."&chamber=".$res2."&apikey=".$APIkey;
            $solution = file_get_contents($url_new);
            $object= json_decode($solution,true);
            foreach($object['results'] as $result)
            {
                if($result['bill_id']==$res1) 
                {  
                    $output = "<style> #T td {text-align:left; } </style><center><table id='T' cellpadding=\"5%\" style=\"border-collapse:collapse\"><tr><td width=60px></td><td width=190px>Bill ID</td><td>".$result['bill_id']."</td><td width=50px></td></tr>";
                    
                    $output .= "<tr><td width=60px></td><td width=190px>Bill Title</td><td>".$result['short_title']."</td><td width=50px></td></tr>";
                
                    $output .="<tr><td width=60px></td><td width=190px>Sponsor</td><td>".$result['sponsor']['title']." ".$result['sponsor']['first_name']." ".$result['sponsor']['last_name']."</td><td width=50px></td></tr>"; 
                    
                    $output .="<tr><td width=60px></td><td width=190px>Introduced On</td><td>".$result['introduced_on']."</td><td width=50px></td></tr>";
                    
                    $output .="<tr><td width=60px></td><td width=190px>Last action with date</td><td>".$result['last_version']['version_name'].", ".$result['last_action_at']."</td><td width=50px></td></tr>";
                    
                    $output .="<tr><td width=60px></td><td width=190px>Bill URL</td><td><a href=\"".$result['last_version']['urls']['pdf']."\" target=\"_blank\">".$result['short_title']."</td><td width=50px></td></tr></table><br></center>";
                }
            }
        }
    
        else 
        {
            $res3 = $_GET['R3']; //bioguide_id
            $res4 = $_GET['R4']; //state
            
            $url_new = 'http://congress.api.sunlightfoundation.com/legislators?chamber='.$rad.'&state='.$res4.'&bioguide_id='.$res3.'&apikey='.$APIkey;
            $solution = file_get_contents($url_new);
            $object = json_decode($solution,true);
            foreach($object['results'] as $result) 
            {
                if($result['bioguide_id']==$res3) 
                {
                    
                    $image="<td colspan=\"4\" style=\"text-align:center;\" ><img src=\"http://theunitedstates.io/images/congress/225x275/".$result['bioguide_id'].".jpg\" alt \"Image not Available\" /></td>";
                
                    $output .= "<style> #T td {text-align:left; }</style><center><table  id='T' cellpadding=\"3%\" ><tr><td ><br/></td></tr><tr>".$image."</tr><tr><td><br/></td></tr>";
                    
                    $output .= "<tr><td width=100px></td><td width=190px>Full Name</td><td>".$result['title']." ".$result['first_name']." ".$result['last_name']."</td><td width=100px></td></tr>";
                
                    $output .="<tr><td width=100px></td><td width=190px>Term Ends on</td><td>".$result['term_end']."</td><td width=100px></td></tr>";
                    
                    $output .="<tr><td width=100px></td><td width=190px>Website</td><td><a target=\"_blank\" href='".$result['website']."' >".$result['website']."</td><td width=100px></td></tr>";
                    
                    $output .="<tr><td width=100px></td><td width=190px>Office</td><td>".$result['office']."</td><td width=100px></td></tr>";
                    
                    $output .="<tr><td width=100px></td><td width=190px>Facebook</td><td><a target=\"_blank\" href='http://www.facebook.com/".$result['facebook_id']."'>".$result['first_name']." ".$result['last_name']."</a></td><td width=100px></td></tr>";
                    
                    $output .="<tr><td width=100px></td><td width=190px>Twitter</td><td><a target=\"_blank\" href='http://www.twitter.com/".$result['twitter_id']."'>".$result['first_name']." ".$result['last_name']."</a></td><td width=100px></td></tr><tr><td><br/></td></tr></table></center>";      
                }
            }
        }
        
}


        
?>
    
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=windows-1252">
    <title>Congress Information</title> 
    <h2 align="center">Congress Information Search</h2>
    
    
    <!--* basic CSS styling for the table -->
    <style type="text/css">
        table {border: 2px solid grey; margin-top: -10px;}
        table td {text-align: center;}
    </style>
    
    <!-- javascript functions -->
    <script>
    
        
    // function to replace keyword with the corresponding prefix
    function replaceKeyword(choice)
    {
        // to replace the keyword descriptor based on the user's choice
        var key_index = choice.selectedIndex;
        var val = choice.options[key_index].value;
        if(val == 'L')
            document.getElementById('changeKey').innerHTML='State/Representative*';
        if(val == 'C')
            document.getElementById('changeKey').innerHTML='Committee ID*';
        if(val == 'B')
            document.getElementById('changeKey').innerHTML='Bill ID*';
        if(val == 'A')
            document.getElementById('changeKey').innerHTML='Amendment ID*';
    }
    
    // function that ensures all the required fields are present
    function validate()
    {  
        //to ensure all the required fields have been entered
        
        alert1=0; 
        alert2=0;
        
        var db=document.getElementById("DBtype");
        var selectedValue = db.options[db.selectedIndex].value;
        if (selectedValue == "not_selected")
            alert1=1;
            
        var key1=document.getElementById("key1").value;
        if(key1=="")
            alert2=1;
            
        if(alert1==1 && alert2==1)
            alert("Please enter the following missing information: Congress database, keyword");
        else if(alert1==1 && alert2==0)
            alert("Please enter the following missing information: Congress database");
        else if(alert1==0 && alert2==1)
            alert("Please enter the following missing information: keyword");   
            
    }
     
    // function that resets the from to its default initial state
    function resetForm(ele) 
    {
        //resets the form to its default state
        document.getElementById('changeKey').innerHTML="Keyword*";
            
        tags = ele.getElementsByTagName('input');
        for(i = 0; i < tags.length; i++) 
        {
            switch(tags[i].type) 
            {
                case 'text':
                tags[i].value = '';
                    
                case 'radio':
                if(tags[i].value=='senate') {tags[i].checked = true;}  
            }
        }

        document.getElementById("DBtype").selectedIndex = 0;
       
    }    

    </script>

</head>
        
    
<body>
    <center>
        
    <form id="CongressForm" action="congress.php" method="post" onsubmit="validate()">

    <table cellpadding="3%">
     
    <tr>
        <td>Congress Database</td>
        <td>
            <select name="choice" id="DBtype" size="1" onchange="replaceKeyword(this.form.choice)" >
                
				<option selected disabled="disabled" value="not_selected" >Select your option</option>
                
				<option name ="Legislators" value="L" 
                        <?php 
                        
                        if (isset($_POST['choice']) && $_POST['choice']=="L" && ($_SESSION['opt1']==0)) 
                        { echo "selected";  $ch=$_SESSION['dropdown']; $_SESSION['opt1']=1;  }
                        
                        else if($_SESSION['opt1']==1) 
                        { echo "selected";  $ch=$_SESSION['dropdown']; $_SESSION['opt1']=0; }
                          
                        ?>
                        >Legislators</option>
                
				<option name ="Committees" value="C" 
                        <?php if (isset($_POST['choice']) && $_POST['choice']=="C") echo "selected";
                        $ch=$_SESSION['dropdown']; //echo ($drop=="C")?'selected':''; ?>>Committees</option>
                
				<option name ="Bills" value="B" 
                        
                        <?php
                         if (isset($_POST['choice']) && $_POST['choice']=="B" && ($_SESSION['opt2']==0)) 
                        { echo "selected";  $ch=$_SESSION['dropdown']; $_SESSION['opt2']=1;  }
                        
                        else if($_SESSION['opt2']==1) 
                        { echo "selected";  $ch=$_SESSION['dropdown']; $_SESSION['opt2']=0; }
                          ?>
                    >Bills</option>
                
				<option name ="Amendments" value="A" 
                        <?php if (isset($_POST['choice']) && $_POST['choice']=="A") echo "selected";
                        $ch=$_SESSION['dropdown'];  //echo ($drop=="A")?'selected':''; ?>>Amendments</option>
                
            </select>    
        </td>
    </tr>
        
    <tr>
        <td>Chamber</td>
        <td>
            <input type = "radio" name = "Chamber" value = "senate" checked="checked" 
                   <?php 
                            $cham=$_SESSION['rad1'];
                            if(isset($_POST['Chamber'])&&($_POST['Chamber']== 'senate')&&($_SESSION['y1']==0))  {echo ' checked="checked"'; $_SESSION['y1']=1;} 
                            else if($_SESSION['y1']==1) {$_SESSION['y1']=0; echo 'checked="checked"';}
                   else echo '';
                            
                   
                   
                    ?> >Senate
            
            <input type = "radio" name = "Chamber" value = "house" 
                   <?php 
                            $cham=$_SESSION['rad1'];
                            if(isset($_POST['Chamber'])&&($_POST['Chamber']== 'house')&&($_SESSION['y2']==0))  {echo ' checked="checked"'; $_SESSION['y2']=1;} 
                            else if($_SESSION['y2']==1) {$_SESSION['y2']=0; echo 'checked="checked"';}
                   else echo '';
                   
                   ?> >House
        </td>
    </tr>
        
        
    <tr>
        <td  id="changeKey">Keyword*
        
            <?php
            
              if(isset($_POST['choice']) && $_POST['choice']=='C') 
              { echo "<script>document.getElementById('changeKey').innerHTML='Committee ID*';</script>"; unset($_POST['choice']);}
            
              if(isset($_POST['choice']) && $_POST['choice']=='A') 
              { echo "<script>document.getElementById('changeKey').innerHTML='Amendment ID*';</script>"; unset($_POST['choice']);}   
                
              if(isset($_POST['choice'])&&($_SESSION['z']==0))
              {
                  if($_POST['choice']=='L')
                  {    
                      echo "<script>document.getElementById('changeKey').innerHTML='State/Representative*';</script>";
                      $_SESSION['key']="State/Representative*";
                      unset($_POST['choice']);
                      $_SESSION['z']=1;
                  }

                  if($_POST['choice']=='B')
                  {
                      echo "<script>document.getElementById('changeKey').innerHTML='Bill ID*';</script>"; unset($_POST['choice']);
                      $_SESSION['key']="Bill ID*";
                      unset($_POST['choice']);
                      $_SESSION['z']=1;
                  }
                     
              }
              else if($_SESSION['z']==1)
              {
                echo "<script>document.getElementById('changeKey').innerHTML='".$_SESSION['key']."';</script>";
                $_SESSION['z']=0;
              }
                   
            ?>    
        
        </td>
        <td >
            <input id="key1" type = "text" name = "keyword"  size="15" 
                   value="<?php 
                          
                          $tbox=$_SESSION['tbox1']; 
                          if(isset($_POST['keyword'])&&($_SESSION['x']==0)) 
                          {
                                echo $_POST['keyword'];
                                $_SESSION['x']=1;
                                        
                          } 
                          else if($_SESSION['x']==1) 
                          {  
                              echo $tbox;
                              $_SESSION['x']=0; 
                          }
                          else echo "";
                          
                          ?>" >
            
        </td>
    </tr>
        
    <tr>
        <td></td>
            <td><input type = "submit" name="submit" value = "Search" > 
            <input name="dest" type="button" value="Clear" onclick=" location.href='congress.php'; resetForm(this.form);"></td> 
    </tr>
		
    <tr>
			<td colspan="2"><a href = "http://sunlightfoundation.com/" target="_blank">Powered by Sunlight Foundation</a></td>
    </tr> 
    
    </table>
        
        
       
    </form>
        
    </center>  
    <noscript></noscript>
    </body>
    
    <?php 
        echo "<br><br>";
        echo $output; 
        
    ?>
    
</html>