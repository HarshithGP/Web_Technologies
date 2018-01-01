<!-- generate a readable random password using a dictionary -->

<?php

error_reporting(0);

function read_dictionary($filename){
    
$dictionary_file=$filename;
return file($dictionary_file, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);
    
}

$basic_words = read_dictionary('friendly_words.txt');
$brand_words = read_dictionary('brand_words.txt');
$words = array_merge($brand_words, $basic_words);

function pick_random_word($array) {
    
    $i = mt_rand(0,count($array)-1);
    return $array[$i];
}

function pick_random_symbol(){
    
    $symbols = '$*><?/+=-!@#$(){}[]%';
    $i = mt_rand(0,strlen($symbols)-1);
    return $symbols[$i];
}

function pick_random_number($digits){
    
    $min = pow(10,($digits-1));
    $max = pow(10,$digits)-1;
    
    return strval(mt_rand($min,$max));
}

function filter_words_by_length($array, $length){
    
    $select_words = array();
    foreach($array as $word){
        if(strlen($word) == $length){
         $select_words[] = $word; 
        }
    }
   
    return $select_words;
}


$length=12;
$word_count=2;
$digit_count=2;
$symbol_count=1;
$avg_wlength = ($length - $digit_count - $symbol_count) / $word_count;
$password="";


$next_wlength = mt_rand($avg_wlength-1,$avg_wlength+1);
$select_words = filter_words_by_length($words,$next_wlength);
$password .= pick_random_word($select_words);

$password .= pick_random_symbol();
$password .= pick_random_number($digit_count);

$next_wlength = $length-strlen($password);
$select_words = filter_words_by_length($words,$next_wlength);
$password .= pick_random_word($select_words);

echo "Friendly Password : " . $password . "<br/>";
echo "Length : ".strlen($password)."<br/";


?>