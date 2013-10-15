// usage: log('inside coolFunc', this, arguments);
// paulirish.com/2009/log-a-lightweight-wrapper-for-consolelog/
window.log = function(){
  log.history = log.history || [];   // store logs to an array for reference
  log.history.push(arguments);
  if(this.console) {
    arguments.callee = arguments.callee.caller;
    var newarr = [].slice.call(arguments);
    (typeof console.log === 'object' ? log.apply.call(console.log, console, newarr) : console.log.apply(console, newarr));
  }
};

// make it safe to use console.log always
(function(b){function c(){}for(var d="assert,count,debug,dir,dirxml,error,exception,group,groupCollapsed,groupEnd,info,log,timeStamp,profile,profileEnd,time,timeEnd,trace,warn".split(","),a;a=d.pop();){b[a]=b[a]||c}})((function(){try
{console.log();return window.console;}catch(err){return window.console={};}})());

// scriptelSigCap plugin
// remap jQuery to $
(function($, undefined){
  function buildLookupTableForGEN() {
    var ret = new Object();
    
    ret.msx = new Array();
    ret.msx[0x29] = 0;
    ret.msx[0x21] = 1;
    ret.msx[0x40] = 2;
    ret.msx[0x23] = 3;
    ret.msx[0x24] = 4;
    ret.msx[0x25] = 5;
    ret.msx[0x5e] = 6;
    ret.msx[0x26] = 7;
    ret.msx[0x2a] = 8;
    ret.msx[0x28] = 9;
    ret.msx[0x41] = 10;
    ret.msx[0x42] = 11;
    ret.msx[0x43] = 12;
    ret.msx[0x44] = 13;
    ret.msx[0x45] = 14;
    ret.msx[0x46] = 15;
    ret.msx[0x47] = 16;
    ret.msx[0x48] = 17;
    ret.msx[0x49] = 18;
    ret.msx[0x4a] = 19;
    ret.msx[0x4b] = 20;
    ret.msx[0x4c] = 21;
    
    ret.lsx = new Array();
    ret.lsx[0x4d] = 0;
    ret.lsx[0x4e] = 1;
    ret.lsx[0x4f] = 2;
    ret.lsx[0x50] = 3;
    ret.lsx[0x51] = 4;
    ret.lsx[0x52] = 5;
    ret.lsx[0x53] = 6;
    ret.lsx[0x54] = 7;
    ret.lsx[0x55] = 8;
    ret.lsx[0x56] = 9;
    ret.lsx[0x57] = 10;
    ret.lsx[0x58] = 11;
    ret.lsx[0x59] = 12;
    ret.lsx[0x5a] = 13;
    ret.lsx[0x22] = 14;
    ret.lsx[0x3c] = 15;
    ret.lsx[0x5f] = 16;
    ret.lsx[0x3e] = 17;
    ret.lsx[0x3f] = 18;
    ret.lsx[0x3a] = 19;
    ret.lsx[0x2b] = 20;
    ret.lsx[0x7b] = 21;
    ret.lsx[0x7c] = 22;
    
    ret.msy = new Array();
    ret.msy[0x30] = 0;
    ret.msy[0x31] = 1;
    ret.msy[0x32] = 2;
    ret.msy[0x33] = 3;
    ret.msy[0x34] = 4;
    ret.msy[0x35] = 5;
    ret.msy[0x36] = 6;
    ret.msy[0x37] = 7;
    ret.msy[0x38] = 8;
    ret.msy[0x39] = 9;
    ret.msy[0x61] = 10;
    ret.msy[0x62] = 11;
    ret.msy[0x63] = 12;
    ret.msy[0x64] = 13;
    ret.msy[0x65] = 14;
    ret.msy[0x66] = 15;
    ret.msy[0x67] = 16;
    ret.msy[0x68] = 17;
    ret.msy[0x69] = 18;
    ret.msy[0x6a] = 19;
    ret.msy[0x6b] = 20;
    ret.msy[0x6c] = 21;
    
    ret.lsy = new Array();
    ret.lsy[0x6d] = 0;
    ret.lsy[0x6e] = 1;
    ret.lsy[0x6f] = 2;
    ret.lsy[0x70] = 3;
    ret.lsy[0x71] = 4;
    ret.lsy[0x72] = 5;
    ret.lsy[0x73] = 6;
    ret.lsy[0x74] = 7;
    ret.lsy[0x75] = 8;
    ret.lsy[0x76] = 9;
    ret.lsy[0x77] = 10;
    ret.lsy[0x78] = 11;
    ret.lsy[0x79] = 12;
    ret.lsy[0x7a] = 13;
    ret.lsy[0x27] = 14;
    ret.lsy[0x2c] = 15;
    ret.lsy[0x2d] = 16;
    ret.lsy[0x2e] = 17;
    ret.lsy[0x2f] = 18;
    ret.lsy[0x3b] = 19;
    ret.lsy[0x3d] = 20;
    ret.lsy[0x5b] = 21;
    ret.lsy[0x5c] = 22;
    
    return ret;
  }
  
    function buildLookupTableForSTN() {
    var ret = new Object();
    
    ret.msx = new Array();
    ret.msx[0x41] = 0;
    ret.msx[0x43] = 1;
    ret.msx[0x45] = 2;
    ret.msx[0x47] = 3;
    ret.msx[0x49] = 4;
    ret.msx[0x4b] = 5;
    ret.msx[0x4d] = 6;
    ret.msx[0x4f] = 7;
    ret.msx[0x51] = 8;
    ret.msx[0x53] = 9;
    ret.msx[0x55] = 10;
    ret.msx[0x57] = 11;
    ret.msx[0x59] = 12;
    ret.msx[0x21] = 13;
    ret.msx[0x23] = 14;
    ret.msx[0x25] = 15;
    ret.msx[0x26] = 16;
    ret.msx[0x28] = 17;
    ret.msx[0x2b] = 18;
    ret.msx[0x7d] = 19;
    ret.msx[0x22] = 20;
    ret.msx[0x3c] = 21;
    
    ret.lsx = new Array();
    ret.lsx[0x42] = 0;
    ret.lsx[0x44] = 1;
    ret.lsx[0x46] = 2;
    ret.lsx[0x48] = 3;
    ret.lsx[0x4a] = 4;
    ret.lsx[0x4c] = 5;
    ret.lsx[0x4e] = 6;
    ret.lsx[0x50] = 7;
    ret.lsx[0x52] = 8;
    ret.lsx[0x54] = 9;
    ret.lsx[0x56] = 10;
    ret.lsx[0x58] = 11;
    ret.lsx[0x5a] = 12;
    ret.lsx[0x40] = 13;
    ret.lsx[0x24] = 14;
    ret.lsx[0x5e] = 15;
    ret.lsx[0x2a] = 16;
    ret.lsx[0x29] = 17;
    ret.lsx[0x5f] = 18;
    ret.lsx[0x7b] = 19;
    ret.lsx[0x7c] = 20;
    ret.lsx[0x3a] = 21;
    ret.lsx[0x3e] = 22;
    
    ret.msy = new Array();
    ret.msy[0x61] = 0;
    ret.msy[0x63] = 1;
    ret.msy[0x65] = 2;
    ret.msy[0x67] = 3;
    ret.msy[0x69] = 4;
    ret.msy[0x6b] = 5;
    ret.msy[0x6d] = 6;
    ret.msy[0x6f] = 7;
    ret.msy[0x71] = 8;
    ret.msy[0x73] = 9;
    ret.msy[0x75] = 10;
    ret.msy[0x77] = 11;
    ret.msy[0x79] = 12;
    ret.msy[0x31] = 13;
    ret.msy[0x33] = 14;
    ret.msy[0x35] = 15;
    ret.msy[0x37] = 16;
    ret.msy[0x39] = 17;
    ret.msy[0x3d] = 18;
    ret.msy[0x5d] = 19;
    ret.msy[0x27] = 20;
    ret.msy[0x2c] = 21;
    
    ret.lsy = new Array();
    ret.lsy[0x62] = 0;
    ret.lsy[0x64] = 1;
    ret.lsy[0x66] = 2;
    ret.lsy[0x68] = 3;
    ret.lsy[0x6a] = 4;
    ret.lsy[0x6c] = 5;
    ret.lsy[0x6e] = 6;
    ret.lsy[0x70] = 7;
    ret.lsy[0x72] = 8;
    ret.lsy[0x74] = 9;
    ret.lsy[0x76] = 10;
    ret.lsy[0x78] = 11;
    ret.lsy[0x7a] = 12;
    ret.lsy[0x32] = 13;
    ret.lsy[0x34] = 14;
    ret.lsy[0x36] = 15;
    ret.lsy[0x38] = 16;
    ret.lsy[0x30] = 17;
    ret.lsy[0x2d] = 18;
    ret.lsy[0x5b] = 19;
    ret.lsy[0x5c] = 20;
    ret.lsy[0x3b] = 21;
    ret.lsy[0x2e] = 22;
    
    return ret;
  }

  function buildLookupTable(isGenModel) {
	if (isGenModel) {
		return buildLookupTableForGEN();
	} else {
		return buildLookupTableForSTN();
	}
  }
  
  function convertToJson(notJson) {
    var scriptelregex = /^\}SCRIPTEL(((([\x40-\x4c]|[\x23-\x26]|[\x28-\x2a]|\x21|\x5e)([\x4d-\x5a]|\x22|\x3c|\x5f|\x3a|\x3e|\x3f|\x2b|\x7b|\x7c))|(([\x4d-\x5a]|\x22|\x3c|\x5f|\x3a|\x3e|\x3f|\x2b|\x7b|\x7c)([\x40-\x4c]|[\x23-\x26]|[\x28-\x2a]|\x21|\x5e)))((([\x30-\x39]|[\x61-\x6c])([\x6d-\x7a]|\x27|[\x2c-\x2f]|\x3b|\x3d|\x5b|\x5c))|(([\x6d-\x7a]|\x27|[\x2c-\x2f]|\x3b|\x3d|\x5b|\x5c)([\x30-\x39]|[\x61-\x6c])))|\x20)*\]$/;
	var isGenModel = false;
	var scriptelPreamble = /^\}SCRIPTEL/;
	if (scriptelPreamble.test(notJson)) { // ST1501-GEN
	  console.log("beginning verified: proceeding to decode");
	  if (scriptelregex.test(notJson)) {
	  	console.log("Complete signature data match.");
	  } else {
	  	console.log("NO MATCH: Do not expect good results.");
	  }
	  isGenModel = true;
    } else { // ST1501-STN
		var testForCapsLock = /^\~stsign/ 
		scriptelPreamble = /^\~STSIGN [A-Z] ST(1401|1501)-STN ([0-9]|\.)* /
		scriptelregex = /^\~STSIGN [A-Z] ST(1401|1501)-STN ([0-9]|\.)* ((\x41|\x43|\x45|\x47|\x49|\x4b|\x4d|\x4f|\x51|\x53|\x55|\x57|\x59|\x21|\x23|\x25|\x26|\x28|\x2b|\x7d|\x22|\x3c)(\x42|\x44|\x46|\x48|\x4a|\x4c|\x4e|\x50|\x52|\x54|\x56|\x58|\x5a|\x40|\x24|\x5e|\x2a|\x29|\x5f|\x7b|\x7c|\x3a|\x3e)(\x61|\x63|\x65|\x67|\x69|\x6b|\x6d|\x6f|\x71|\x73|\x75|\x77|\x79|\x31|\x33|\x35|\x37|\x39|\x3d|\x5d|\x27|\x2c)(\x62|\x64|\x66|\x68|\x6a|\x6c|\x6e|\x70|\x72|\x74|\x76|\x78|\x7a|\x32|\x34|\x36|\x38|\x30|\x2d|\x5b|\x5c|\x3b|\x2e)* )*/

		if (testForCapsLock.test(notJson)) {
			console.log("caps detected");
			for (var j = 0; j < notJson.length; j++) {
				if ( notJson.charAt(j).toUpperCase() != notJson.charAt(j) ) {
					//character was lowercase so should be set to uppercase
					notJson = notJson.substr(0,j) + notJson.charAt(j).toUpperCase() + notJson.substr(j+1);
				} else if (notJson.charAt(j).toLowerCase() != notJson.charAt(j)) {
					//character was uppercase should should be made lowercase
					notJson = notJson.substr(0,j) + notJson.charAt(j).toLowerCase() + notJson.substr(j+1);
				}
			}
			console.log(notJson);
		} else {
			console.log("not caps");
		}
		
		if (scriptelregex.test(notJson)) {
			console.log("Complete signature data match.");
		} else {
			console.log("NO MATCH: Do not expect good results.");
		}
    }
	
    var decoder = buildLookupTable(isGenModel);
    var returnJSON = "[";

    var beginingOfCoords = scriptelPreamble.exec(notJson)[0].length;

    for (var i = beginingOfCoords; i+4 < notJson.length-1; i += 4) {
      var msx = 0;
      var lsx = 0;
      var msy = 0;
      var lsy = 0;
      while(notJson.charAt(i) == " ") {
        returnJSON += "{},"
        i++;
      }
      while ((typeof decoder.msx[notJson.charCodeAt(i)] === 'undefined') && (typeof decoder.lsx[notJson.charCodeAt(i)] === 'undefined') && i < notJson.length-1) {
        console.log("missing lsx or msx at " + i + ", value: " + notJson.charCodeAt(i) + ", char: " + notJson.charAt(i));
        if(notJson.charAt(i) == " ") {
          returnJSON += "{},"
        }
        i++;
      }
      
      if (i+3 > notJson.length - 1) continue;
      
      if ((typeof decoder.msx[notJson.charCodeAt(i)] !== 'undefined') && (typeof decoder.lsx[notJson.charCodeAt(i+1)] !== 'undefined')) {
        msx = decoder.msx[notJson.charCodeAt(i)];
        lsx = decoder.lsx[notJson.charCodeAt(i+1)];
      } else if ((typeof decoder.msx[notJson.charCodeAt(i+1)] !== 'undefined') && (typeof decoder.lsx[notJson.charCodeAt(i)] !== 'undefined')) {
        msx = decoder.msx[notJson.charCodeAt(i+1)];
        lsx = decoder.lsx[notJson.charCodeAt(i)];
      } else {
        // How did I get here?
        console.log("unexpected error at " + i);
      }

      if ((typeof decoder.msy[notJson.charCodeAt(i+2)] === 'undefined') && (typeof decoder.lsy[notJson.charCodeAt(i+2)] === 'undefined')) {
        console.log("missing lsy or msy at " + (i+2) + ", value: " + notJson.charCodeAt(i+2) + ", char: " + notJson.charAt(i+2));
        continue;
      }
        
      if ((typeof decoder.msy[notJson.charCodeAt(i+2)] !== 'undefined') && (typeof decoder.lsy[notJson.charCodeAt(i+3)] !== 'undefined')) {
        msy = decoder.msy[notJson.charCodeAt(i+2)];
        lsy = decoder.lsy[notJson.charCodeAt(i+3)];
      } else if ((typeof decoder.msy[notJson.charCodeAt(i+3)] !== 'undefined') && (typeof decoder.lsy[notJson.charCodeAt(i+2)] !== 'undefined')) {
        msy = decoder.msy[notJson.charCodeAt(i+3)];
        lsy = decoder.lsy[notJson.charCodeAt(i+2)];
      } else {
        // How did I get here?
        console.log("unexpected error at " + i);
      }
      
      var x = (msx*23+lsx)+1;
      var y = (msy*23+lsy)*0.2;
      returnJSON += '{"x":' + x + ',"y":' + y + "}";
      if (i+8 < notJson.length-1) {
        returnJSON += ","
      }
    }

    if (returnJSON.charAt(returnJSON.length-1) == ',') {
        returnJSON += "{}";
    }
    
    returnJSON += "]";
    var endOfChar = isGenModel ? ']' : '`';
    if (notJson.charAt(notJson.length-1) != endOfChar) {
      console.log("ending NOT verified");
    } else {
      console.log("ending verified: finishing");
    }
    return returnJSON;
  }
  
  // http://docs.jquery.com/Plugins/Authoring
  var methods = {
    init : function(options) {
      return this.each(function() {
        var $this = $(this),
            data = $this.data('scriptelSigCap');
      
        // If the plugin hasn't been initialized yet
        if (!data) {
          
          $this.data('scriptelSigCap', {
            canvas : undefined,
            ctx : undefined,
            stroking : false,
            enabled : true,
            lastX : 0,
            plastY : 0,
            stroke : [],
            width : 0,
            height : 0                    
          });
          data = $this.data('scriptelSigCap');
          
          // begin absurd limit check
          var myWidth = $this.width();
          var myHeight = $this.height();
          if (myWidth < 400) myWidth = 500;
          if (myHeight < 100) myHeight = 100;
          // end absurd limit check
          
          var canvasid=$this.attr('id')+'-canvas';
          // Need to add canvas element to $this
          data.width = myWidth;
          data.height = myHeight;
          var c = document.createElement('canvas');
          $(c).attr('width', myWidth).attr('height', myHeight).attr('id', canvasid).attr('cursor', 'pointer').attr('style', 'position:absolute;top:0;left:0;');
          
          if (typeof G_vmlCanvasManager != 'undefined') {
            c = G_vmlCanvasManager.initElement(c);
          }
          
          data.canvas = $(c).get(0);
          $this.prepend(data.canvas);
          
          // Context is used for all drawing commands
          data.ctx = data.canvas.getContext("2d");
                            
        }
        $this.scriptelSigCap('clear');
      });
    },
    clear : function() {
      return this.each(function() {
        var $this = $(this),
            data = $this.data('scriptelSigCap');
        data.stroke = [];
        data.ctx.save();
        data.ctx.scale(1.0,1.0);
        data.ctx.clearRect(0,0,data.canvas.width, data.canvas.height);
        data.ctx.fillStyle = "rgb(241,241,241)";
        data.ctx.fillRect(0,0,data.canvas.width, data.canvas.height);
        data.ctx.fillStyle = "rgba(0,0,0,0.1)";
//        if(data.ctx.fillText) {
//          // magically encoded 50 as height
//          data.ctx.font = "" + Math.round(data.canvas.height / 2) +"px sans-serif";
//          data.ctx.textAlign = "center";
//          data.ctx.fillText("X", data.canvas.width * 0.05833, data.canvas.height * 0.68);
//        }
        data.ctx.lineWidth = 1;
        data.ctx.strokeStyle = "rgba(0,0,0,0.1)";
        data.ctx.beginPath();
//        data.ctx.moveTo(data.canvas.width * 0.0416, data.canvas.height * 0.85);
//        data.ctx.lineTo(data.canvas.width * 0.966, data.canvas.height * 0.85);
        data.ctx.stroke();
        data.ctx.restore();
      
      });
    },
    draw : function(encodedData) {
      return this.each(function() {
        var $this = $(this),
            data = $this.data('scriptelSigCap');
        data.stroke = $.parseJSON(encodedData);
        if (data.stroke == null) { data.stroke = []; }
        data.ctx.save();
        data.ctx.beginPath();
        data.ctx.lineCap = "round";
        data.ctx.lineJoin = "round";
        data.ctx.lineWidth = 2;
        data.ctx.strokeStyle = "rgba(0,0,0,1)";
        // this fixes relative position problems
        var drawing = false;
        for (var i=0, len=data.stroke.length; i<len; ++i) {
          var item = data.stroke[i];
          if (item.x) {
            if (drawing) {
              data.ctx.lineTo(item.x, item.y);
            } else {
              data.ctx.moveTo(item.x, item.y);
              data.ctx.lineTo(item.x, item.y);
              drawing = true;
            }
          } else {
            drawing = false;
          }
        }
        data.ctx.stroke();
        data.ctx.restore();
      });
    },
    show : function(encodedData) {
      // It seems really odd to do a $('div').scriptelSigCap('show', encodedData);
      // We support it. Just don't ask why.
      // Usual use case $('#sig').scriptelSigCap('show', encodedData);
      // Where the $() selector is used on a SINGLE element
      return this.each(function() {
        var $this = $(this),
          data = $this.data('scriptelSigCap');
        $this.scriptelSigCap('clear');
        $this.scriptelSigCap('draw', encodedData);
      });
    },
    showScriptel : function(encodedData) {
      return this.each(function() {
        var $this = $(this);
        $this.scriptelSigCap('show', convertToJson(encodedData));
      });
    },
    destroy : function() {
      return this.each(function() {
        var $this = $(this),
            data = $this.data('scriptelSigCap');
      
        $(window).unbind('.scriptelSigCap');
        data.ctx.remove();
        data.stroke.remove();
        data.canvas.remove();
        $this.removeData('scriptelSigCap');
      });
    }
  };

  // jQuery function namespacing
  //
  $.fn.scriptelSigCap = function( method ) {
    if ( methods[method] ) {
      return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
    } else if ( typeof method === 'object' || ! method ) {
      return methods.init.apply( this, arguments );
    } else {
      $.error( 'Method ' +  method + ' does not exist on jQuery.scriptelSigCap' );
    }
  };
})(this.jQuery);



