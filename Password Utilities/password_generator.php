<!-- generate random password 

using character sets - uppercase/lowercase letters, digits and symbols 

password generated for new users

-->

<?php

error_reporting(0);
//using PHP RANGE - returns array
$lower = implode('',range('a','z')); 
$upper = implode('',range('A','Z'));
$numbers = implode('',range(0,9));


//function generates a random character from a character set
function random_char($string) {
    
    $i=mt_rand(0,strlen($string)-1);
    //rand uses libc random number generator
    //mt_rand - faster and more algorithm used is more random
    return $string[$i];
}

//function generates a string of random charaters of a specfied length
function random_string($length, $char_set){
    
    $output="";
    for($i=0;$i<$length;$i++){
        $output .= random_char($char_set);
    }

    return $output;
}

$options = array(

    'length' => $_GET['length'],
    'lower' => $_GET['lower'],
    'upper' => $_GET['upper'],
    'numbers' => $_GET['numbers'],
    'symbols' => $_GET['symbols']
);

function generate_password($options){
    
    $lower = "abcdefghijklmnopqrstuvwxyz";
    $upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    $numbers = "0123456789";
    $symbols= "[]{}()&^%$#@!/|+-=*";
    $chars = '';
    
    $use_lower = isset($options['lower']) ? $options['lower'] : '0';
    if($use_lower == '1') {$chars .= $lower;}
    
    $use_upper = isset($options['upper']) ? $options['upper'] : '0';
    if($use_upper == '1') {$chars .= $upper;}
    
    $use_numbers = isset($options['numbers']) ? $options['numbers'] : '0';
    if($use_numbers == '1') {$chars .= $numbers;}
    
    $use_symbols = isset($options['symbols']) ? $options['symbols'] : '0';
    if($use_symbols == '1') {$chars .= $symbols;}
    
    $length=isset($options['length']) ? $options['length']:'8';
    return random_string($length,$chars);
}

$password = generate_password($options);

?>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Password Generator</title>    
    </head>
    
    <body>
        <p>Generated Password : <?php echo $password; ?></p>
    
        <p>Generate a new password using the form options </p>
        <form action="" method="GET">
            Length: <input type="text" name="length" value="<?php if(isset($_GET['length'])) {echo $_GET['length'];} else {echo "8";} ?>" /><br/>
            
            <input type="checkbox" name="lower" value="1" <?php  if($_GET["lower"]==1) { echo 'checked'; } ?> />Lowercase<br>
            
            <input type="checkbox" name="upper" value="1" <?php if($_GET['upper']==1) {echo 'checked';} ?> />Uppercase<br>
            
            <input type="checkbox" name="numbers" value="1" <?php if($_GET['numbers']==1) {echo "checked";} ?> />Numbers<br>
            
            <input type="checkbox" name="symbols" value="1" <?php if($_GET['symbols']==1) {echo "checked";} ?> />Symbols<br>
            
            <input type="submit" value="Submit" />
        
        </form>
    </body>

</html>