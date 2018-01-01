<?php

error_reporting(E_ALL ^ E_NOTICE);
error_reporting(E_ERROR | E_PARSE);
$APIkey="b2aff0a1fbed4ac08b2daefb56f7c9a4";

    if($_GET['find']=="Senators")
    {
        //$url_leg="http://104.198.0.197:8080/legislators?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all";
        $url_cong_leg='https://congress.api.sunlightfoundation.com/legislators?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all';
        $LEG=file_get_contents($url_cong_leg);
        echo $LEG;   
    }

    if($_GET['find']=="BillS")
    {
        //$url_bill="http://104.198.0.197:8080/bills?apikey=apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=50";
        $url_cong_bill='https://congress.api.sunlightfoundation.com/bills?apikey=apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=50';
        $BILL=file_get_contents($url_cong_bill);
        echo $BILL;   
    }

    if($_GET['find']=="CommitteeS")
    {
        //$url_commt="http://104.198.0.197:8080/committees?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all";
        $url_cong_commt='https://congress.api.sunlightfoundation.com/committees?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all';
        $COMMT=file_get_contents($url_cong_commt);
        echo $COMMT;   
    }
    
/*
$url_cong_leg='https://congress.api.sunlightfoundation.com/legislators?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all';
$url_cong_bill='https://congress.api.sunlightfoundation.com/bills?apikey=apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=50';
$url_cong_commt='https://congress.api.sunlightfoundation.com/committees?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all';
  

$url_leg="http://104.198.0.197:8080/legislators?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all";
$url_bill="http://104.198.0.197:8080/bills?apikey=apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=50";
$url_commt="http://104.198.0.197:8080/committees?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all";
*/
?>