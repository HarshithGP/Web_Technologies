var app = angular.module('Congress_Info', [ 'angularUtils.directives.dirPagination' ]);

app.controller('congress', function($scope, $http){
               
    $scope.L = function() {
        document.getElementById("legislator").style.display= "block";
        document.getElementById("bill").style.display="none";
        document.getElementById("committee").style.display="none";
        document.getElementById("favourites").style.display="none";
        
    }
    $scope.B = function() {
        document.getElementById("legislator").style.display="none";
        document.getElementById("bill").style.display="block";
        document.getElementById("committee").style.display="none";
        document.getElementById("favourites").style.display="none";
    }
    $scope.C = function() {
        document.getElementById("legislator").style.display="none";
        document.getElementById("committee").style.display="block";
        document.getElementById("bill").style.display="none";
        document.getElementById("favourites").style.display="none";
    }
    $scope.F =function() {
        document.getElementById("legislator").style.display="none";
        document.getElementById("bill").style.display="none";
        document.getElementById("committee").style.display="none";
        document.getElementById("favourites").style.display="block";
    }
              
});
                                        
app.controller('listdata',function ($scope, $http){
   
    
	$scope.users = []; //declare an empty array
    $scope.states = ['Alabama','Alaska','Arizona','Arkansas','California','Colorado','Connecticut','Delaware','District Of Columbia','Florida',
                  'Georgia','Hawaii','Idaho','Illinois','Indiana','Iowa','Kansas','Kentucky','Louisiana','Maine','Maryland','Massachusetts',
                  'Michigan','Minnesota','Mississippi','Missouri','Montana','Nebraska','Nevada','New Hampshire','New Jersey','New Mexico','New York',
                  'North Carolina','North Dakota','Ohio','Oklahoma','Oregon','Pennsylvania','Rhode Island','South Carolina','South Dakota',
                  'Tennessee','Texas','Utah','Vermont','Virginia','Washington','West Virginia','Wisconsin','Wyoming'];
    
    
    $http({
            method:'GET',
            url:'hw8.php',
            params: { find : "Senators"}
    }).success(function (response) {

        $scope.users = response.results;
        
    }, function myError(response) {
         
    });
    
    /* 
    $http({ method : "GET", url : "http://104.198.0.197:8080/legislators?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all"}).success(function mySucces(response) { $scope.users = response.results; }, function myError(response) { $scope.myWelcome = response.statusText;});*/
    
    $scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
    }
    
    $scope.sort1 = function(keyname1){
        $scope.sortKey1 = keyname1;   //set the sortKey to the param passed
    }
     
    $scope.leg_senate = function() {
        
        document.getElementById("legState").style.display="none";
        document.getElementById("legHouse").style.display="none";
        document.getElementById("legSenate").style.display="block";
        
    }
    $scope.leg_house = function() {
        
        document.getElementById("legState").style.display="none";
        document.getElementById("legHouse").style.display="block";
        document.getElementById("legSenate").style.display="none";
        
    }
    $scope.leg_all = function() {
        
        document.getElementById("legState").style.display="block";
        document.getElementById("legHouse").style.display="none";
        document.getElementById("legSenate").style.display="none";
    }
    
    $scope.details = function(user) {
        $scope.senator = user;
    }
    
   
    
});

app.controller('billdata',function($scope, $http){
	$scope.bills =[]; //declare an empty array

    /*$http.get("http://104.198.0.197:8080/bills?apikey=apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=50").success(function(response1){ 
        $scope.bills=response1.results;  //ajax request to fetch data into $scope.data 
	});*/
    
    $http({
            method:'GET',
            url:'hw8.php',
            params: { find : "BillS"}
    }).success(function (response) {

        $scope.bills = response.results;
        
    }, function myError(response) {
         
    });
    
    $scope.bills_new = [];
    $.each($scope.bills, function(){
        if (this.history.active == 'false')
        {
            $scope.bills_new.push(this);
        }
        
    });
    
    /* $http({ method : "GET", url : "http://104.198.0.197:8080/bills?apikey=apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=50"}).success(function mySucces(response) { $scope.bills = response.results; }, function myError(response) { $scope.myWelcome = response.statusText;});*/
    
    $scope.sort1 = function(keyname1){
    $scope.sortKey1 = keyname1;   //set the sortKey to the param passed
    }
    
    $scope.billact = function() {
        document.getElementById("activebill").style.display="block";
        document.getElementById("newbill").style.display="none";
    }
    $scope.billnew = function() {
        document.getElementById("newbill").style.display="block";
        document.getElementById("activebill").style.display="none";
        
    }
    
    $scope.Bill_details = function(bill){
        $scope.BillDet = bill;
    }
    
});

app.controller('commdata',function($scope, $http){
	$scope.comms =[]; //declare an empty array

    $http({
            method:'GET',
            url:'hw8.php',
            params: { find : "CommitteeS"}
    }).success(function (response) {

        $scope.comms = response.results;
        
    }, function myError(response) {
         
    });
    
    /*
    $http({ method : "GET", url : "http://104.198.0.197:8080/committees?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all"}).success(function mySucces(response) { $scope.comms = response.results; }, function myError(response) { $scope.myWelcome = response.statusText;});
    */
    
    $scope.sort2 = function(keyname2){
    $scope.sortKey2 = keyname2;   //set the sortKey to the param passed
    }
    
    $scope.comt_senate = function() {
        document.getElementById("comt_senate").style.display="block";
        document.getElementById("comt_house").style.display="none";
        document.getElementById("comt_joint").style.display="none";
    }
    $scope.comt_house = function() {
        document.getElementById("comt_senate").style.display="none";
        document.getElementById("comt_house").style.display="block";
        document.getElementById("comt_joint").style.display="none";
    }
    $scope.comt_joint = function() {
       document.getElementById("comt_senate").style.display="none";
        document.getElementById("comt_house").style.display="none";
        document.getElementById("comt_joint").style.display="block";
    }

    
});

app.controller('favdata',function($scope, $http){
    
    $scope.favlegis = function() {
        document.getElementById("favLeg").style.display="block";
        document.getElementById("favBill").style.display="none";
        document.getElementById("favComm").style.display="none";
    }
    $scope.favbills = function() {
        document.getElementById("favLeg").style.display="none";
        document.getElementById("favBill").style.display="block";
        document.getElementById("favComm").style.display="none";
    }
    $scope.favcommt = function() {
        document.getElementById("favLeg").style.display="none";
        document.getElementById("favBill").style.display="none";
        document.getElementById("favComm").style.display="block";
    }
   
});


<!-- Menu Toggle Script -->
  
$("#menu-toggle").click(function(e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
});


$('button[data-slide="prev"]').click(function() {
    $('#LEGCarousel').carousel('prev');
});

$('button[data-slide="prev"]').click(function() {
    $('#BILLCarousel').carousel('prev');
});

$('#click_star').click(function() {
    $("span", this).toggleClass("glyphicon glyphicon-star glyphicon glyphicon-star-empty");
});

$('.LEGcarousel').carousel({
    pause: true,
    interval: false
});

$('.BILLCarousel').carousel({
    pause: true,
    interval: false
});      