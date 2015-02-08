<!DOCTYPE html>
<html>
  
  <head>
   
    <meta charset="utf-8">
    
    <title>${title}</title>
    
    
    <style type="text/css">
      html, body, #map-canvas { height: 100%; margin: 0; padding: 0;} 
    </style>
     <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?libraries=geometry,visualization"></script>
     <script src="js/jQuery.js"></script>
     <script type="text/javascript" src = "js/jquery.csv-0.64.js"></script>
     </head>
     <body>
    <div id="map-canvas"></div>
  	
  	<p hidden><pre id="positives">${positives}</pre></p>
  	<p hidden><pre id="negatives">${negatives}</pre></p>
  	
  
     <script type="text/javascript">
      
      var test = "5, 41.8336, -71.4118\n1, 41.8303, -71.4215\n5, 41.8234, -71.3699\n5, 41.8134, -71.3729";
      var test2 = "5, 41.8136, -71.4418\n1, 41.7303, -71.4415\n5, 41.8239, -71.3499\n5, 41.8834, -71.3129";

      function initialize() {
        var mapOptions = {
          center: { lat: 39.2465, lng: -98.3782},
          zoom: 4
        };
        var map = new google.maps.Map(document.getElementById('map-canvas'),
            mapOptions);
		//var x = document.getElementById('positives');
		//var y = x.value();
		var x = $('#positives');
		var y = x.text();
        eqfeed_callback($('#positives').text(), $('#negatives').text(), map);
      }

      function eqfeed_callback(positiveInput, negativeInput, map){
        var positiveData = [];
        var negativeData = [];

        //var positive=$.csv.toArrays(positiveInput);
        //var negative=$.csv.toArrays(negativeInput);
        
        var posData = positiveInput.split('\n');
        var negData = negativeInput.split('\n');
        
        var positive = new Array(posData.length);
        for(i = 0; i < posData.length; i++) {
        	positive[i] = posData[i].split(',');
        }
        
        var negative = new Array(negData.length);
        for(i = 0; i < negData.length; i++) {
        	negative[i] = negData[i].split(',');
        }
        
        for(var i = 0; i<positive.length-1; i++){
          var latLng = new google.maps.LatLng(positive[i][1], positive[i][2]);
          var score = parseInt(positive[i][0], 10)*1000;
          var weightedLoc = {
            location: latLng,
            weight: score
          };
          positiveData.push(weightedLoc);
        };

        for(var i = 0; i<negative.length-1; i++){
          var latLng = new google.maps.LatLng(negative[i][1], negative[i][2]);
          var score = (-1)*parseInt(negative[i][0], 10)*1000;
          var weightedLoc = {
            location: latLng,
            weight: score
          };
          negativeData.push(weightedLoc);
        };
        
        var posArray = new google.maps.MVCArray(positiveData);
        posHeatmap = new google.maps.visualization.HeatmapLayer({
          data: posArray,
          gradient: ['rgba(0, 255, 255, 0)',  'rgb(255, 255, 0)', 'rgb(255, 141, 9)', 'rgb(255, 0, 0)'],//, 'rgb(128, 0, 128)', 'rgb(255, 0, 255)'], 
          radius: 50,
          map: map
        });

        var negArray = new google.maps.MVCArray(negativeData);
        negHeatmap = new google.maps.visualization.HeatmapLayer({
          data: negArray,
          gradient: ['rgba(0, 255, 255, 0)', 'rgba(1, 255, 248, 0.3)', 'rgba(10, 98, 250, 0.5)', 'rgba(7, 0, 201, 0.7)'], 
          radius:50,
          map: map
        });
        
      }

      google.maps.event.addDomListener(window, 'load', initialize);
    </script>
     
  </body>

</html>
