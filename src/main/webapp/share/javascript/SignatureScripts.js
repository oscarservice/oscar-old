var pvcnv = document.getElementById("preview");
var pv = new jsGraphics(pvcnv);


var StrokeColor = "black";
var StrokeThickness = 2;
var x0 = 0;
var y0 = 0;

var SubmitData = new Array();
var DrawData = new Array();
var TempData = new Array();
var MouseDown = false;
var FreehandSwitch = true;
var DrawSwitch = false;
var DrawTool = "Freehand";
var Xposition = new Array();
var Yposition = new Array();

var x0_1 = 0;
var y0_1 = 0;
var SubmitData1 = new Array();
var DrawData1 = new Array();
var TempData1 = new Array();
var MouseDown1 = false;
var FreehandSwitch1 = true;
var DrawTool1 = "Freehand";
var Xposition1 = new Array();
var Yposition1 = new Array();
var DrawSwitch1 = false;


function getElementIdPrefix(which) {
	if (undefined == which || null == which) {
		return "";
	}
	var prefixId = which.id.substr(0, which.id.indexOf("-"));
	if (0 == prefixId.length) {
		prefixId = which.id;
	}
	return prefixId;
}

function SetMouseDown(which){
	var divId = getElementIdPrefix(which);
	if ("SignCanvas" == divId) {
		MouseDown = true;
	} else {
		MouseDown1 = true;
	}
}

function SetMouseUp(which){
	var divId = getElementIdPrefix(which);
	if ("SignCanvas" == divId) {
		MouseDown = false;
	} else {
		MouseDown1 = false;
	}
}


function SetDrawOn(which){
	var divId = getElementIdPrefix(which);
	if ("SignCanvas" == divId) {
		DrawSwitch = true;
	} else {
		DrawSwitch1 = true;
	}
}

function SetDrawOff(which){
	var divId = getElementIdPrefix(which);
	if ("SignCanvas" == divId) {
		DrawSwitch = false;
	} else {
		DrawSwitch1 = false;
	}
}

function RoundTo(n, d){
    //rounds n to the nearest d
    var i = Math.round(n / d) * d;
    return i;
}

function SetStart(which){
	var divId = getElementIdPrefix(which);
	if ("SignCanvas" == divId) {
		x0 = (mousex - cnvLeft);
    	y0 = (mousey - cnvTop);
	} else {
		x0_1 = (mousex - cnvLeft1);
    	y0_1 = (mousey - cnvTop1);
	}
    
}

function GetXY(which, x, y){
    var t = StrokeThickness;
	
	var divId = getElementIdPrefix(which);
	if ("SignCanvas" == divId) {
		var l = Xposition.length - 1; //l = last position
	    var h = Math.sqrt(Math.pow((Xposition[l] - x), 2) + Math.pow((Yposition[l] - y), 2)) //calc hypotenuse
	    if (Xposition.length < 2) {
	        Xposition.push(x);
	        Yposition.push(y);
	    }
	    else {
	        if (h > t) {
	            Xposition.push(x);
	            Yposition.push(y);
	        }
	    }
	} else {
		var l = Xposition1.length - 1; //l = last position
	    var h = Math.sqrt(Math.pow((Xposition1[l] - x), 2) + Math.pow((Yposition1[l] - y), 2)) //calc hypotenuse
	    if (Xposition1.length < 2) {
	        Xposition1.push(x);
	        Yposition1.push(y);
	    }
	    else {
	        if (h > t) {
	            Xposition1.push(x);
	            Yposition1.push(y);
	        }
	    }
	}
}

function ClearXY(which){
	var divId = getElementIdPrefix(which);
	if ("SignCanvas" == divId) {
		Xposition = [];
    	Yposition = [];
	} else {
		Xposition1 = [];
   		Yposition1 = [];	
	}
}

function ArrToStr(Arr, s){
    //convert array values to string
    var Str = "";
    for (n = 0; (n < Arr.length); n++) {
        if (n > 0) {
            Str += s; // each set of data separated by s
        }
        Str += Arr[n];
    }
    return Str;
}

function StrToArr(Str, s){
    //converts string to an array
    var Arr = Str.split(s);
    for (n = 0; n < Arr.length; n++) {
        Arr[n] = parseInt(Arr[n]);
    }
    return Arr;
}

function AddFreehand(which, canvas, x, y, StrokeColor){
	var divId = getElementIdPrefix(which);
	if ("SignCanvas" == divId) {
		var X = Xposition;
	    var Y = Yposition;
	    jg.setColor(StrokeColor);
	    jg.setStroke(StrokeThickness);
	    if (X.length > 1) {
	        var a = X.length - 2;
	        var b = a + 1;
	        var x1 = parseInt(X[a]);
	        var y1 = parseInt(Y[a]);
	        //var x2 = parseInt(X[b]);
	        //var y2 = parseInt(Y[b]);
	        jg.drawLine(x1, y1, x, y);
	        jg.paint();
	    }
	} else {
		var X = Xposition1;
	    var Y = Yposition1;
	    jg1.setColor(StrokeColor);
	    jg1.setStroke(StrokeThickness);
	    if (X.length > 1) {
	        var a = X.length - 2;
	        var b = a + 1;
	        var x1 = parseInt(X[a]);
	        var y1 = parseInt(Y[a]);
	        //var x2 = parseInt(X[b]);
	        //var y2 = parseInt(Y[b]);
	        jg1.drawLine(x1, y1, x, y);
	        jg1.paint();
	    }
	}
}

function DrawFreehand(which, canvas, X, Y, StrokeColor){
    canvas.setColor(StrokeColor);
    canvas.setStroke(StrokeThickness);
    canvas.drawPolyline(X, Y);
    canvas.paint();
    //store parameters in an array
    var StrX = ArrToStr(X, ':');
    var StrY = ArrToStr(Y, ':');
    var Parameter = "Freehand" + "|" + StrX + "|" + StrY + "|" + StrokeColor;
	var divId = getElementIdPrefix(which);
	if ("SignCanvas" == divId) {
		DrawData.push(Parameter);
    	document.getElementById(divId + "-DrawData").value = DrawData;
	} else {
		DrawData1.push(Parameter);
    	document.getElementById(divId + "-DrawData").value = DrawData1;
	}
}

function DrawMarker(which){
	var divId = getElementIdPrefix(which);
	if ("SignCanvas" == divId) {
		if (DrawSwitch) {
	        var x = parseInt(mousex - cnvLeft);
	        var y = parseInt(mousey - cnvTop);
	        if (FreehandSwitch) {
				DrawFreehand(which, jg, Xposition, Yposition, StrokeColor);
	            ClearXY(which);
	        }
	    }
	} else {
		if (DrawSwitch1) {
	        var x = parseInt(mousex - cnvLeft1);
	        var y = parseInt(mousey - cnvTop1);
	        if (FreehandSwitch1) {
				DrawFreehand(which, jg1, Xposition1, Yposition1, StrokeColor);
	            ClearXY(which);
	        }
	    }
	}
}

function DrawPreview(which){
	var divId = getElementIdPrefix(which);
	var x = 0, y = 0;
	if ("SignCanvas" == divId) {
		x = parseInt(mousex - cnvLeft);
		y = parseInt(mousey - cnvTop);
		if (MouseDown) {
	        if (FreehandSwitch) {
	            GetXY(which, x, y);
	            AddFreehand(which, pv, x, y, StrokeColor);
	        }
	    }
	} else {
		x = parseInt(mousex - cnvLeft1);
		y = parseInt(mousey - cnvTop1);
		if (MouseDown1) {
	        if (FreehandSwitch1) {
	            GetXY(which, x, y);
	            AddFreehand(which, pv, x, y, StrokeColor);
	        }
	    }
	}
}

function RedrawImage(which, RedrawParameter){
	var divId = getElementIdPrefix(which);
    var DrawingType = RedrawParameter[0];
    if (DrawingType == "Freehand") {
        var X = StrToArr(RedrawParameter[1], ':');
        var Y = StrToArr(RedrawParameter[2], ':');
        StrokeColor = RedrawParameter[3];
		if ("SignCanvas" == divId) {
			DrawFreehand(which, jg, X, Y, StrokeColor);			
		} else {
			DrawFreehand(which, jg1, X, Y, StrokeColor);
		}
    }
}

function Undo(which){
    var divId = getElementIdPrefix(which);
    if ("SignCanvas" == divId) {
        jg.clear();
		TempData = DrawData;
	    DrawData = new Array();
	    document.getElementById(divId + "-DrawData").value = "";
	    for (i = 0; (i < (TempData.length - 1)); i++) {
	        var Parameters = TempData[i].split("|");
	        RedrawImage(which, Parameters);
	    }
    }
    else {
        jg1.clear();
		TempData1 = DrawData1;
	    DrawData1 = new Array();
	    document.getElementById(divId + "-DrawData").value = "";
	    for (i = 0; (i < (TempData1.length - 1)); i++) {
	        var Parameters = TempData1[i].split("|");
	        RedrawImage(which, Parameters);
	    }
    }
}


function Clear(which){
    var divId = getElementIdPrefix(which);
    if ("SignCanvas" == divId) {
        jg.clear();
		TempData = new Array();
	    DrawData = new Array();
	    SubmitData = new Array();
	    document.getElementById(divId + "-TempData").value = "";
	    document.getElementById(divId + "-DrawData").value = "";
	    document.getElementById(divId + "-SubmitData").value = "";
		document.getElementById(divId + "-signature").value = "";
	    Xposition = new Array();
	    Yposition = new Array();
    }
    else {
        jg1.clear();
		TempData1 = new Array();
	    DrawData1 = new Array();
	    SubmitData1 = new Array();
	    document.getElementById(divId + "-TempData").value = "";
	    document.getElementById(divId + "-DrawData").value = "";
	    document.getElementById(divId + "-SubmitData").value = "";
		document.getElementById(divId + "-signature").value = "";
	    Xposition1 = new Array();
	    Yposition1 = new Array();
    }
}

function ClearExceptSubmit(which){
    var divId = getElementIdPrefix(which);
    if ("SignCanvas" == divId) {
        jg.clear();
		TempData = new Array();
	    DrawData = new Array();
	    document.getElementById(divId + "-TempData").value = "";
	    document.getElementById(divId + "-DrawData").value = "";
		document.getElementById(divId + "-signature").value = "";
	    Xposition = new Array();
	    Yposition = new Array();
    }
    else {
        jg1.clear();
		TempData1 = new Array();
	    DrawData1 = new Array();
	    document.getElementById(divId + "-TempData").value = "";
	    document.getElementById(divId + "-DrawData").value = "";
		document.getElementById(divId + "-signature").value = "";
	    Xposition1 = new Array();
	    Yposition1 = new Array();
    }
}

function SubmitImage(){
	$("div.sig").each(function(){
		EncodeData(this);
	});
}

function EncodeData(which){
    var packed = ""; // Initialize packed or we get the word 'undefined'
    //Converting image data in array into a string
	var divId = getElementIdPrefix(which);
    if ("SignCanvas" == divId) {
		for (i = 0; (i < DrawData.length); i++) {
	        if (i > 0) {
	            packed += ","; // each set of data separated by comma
	        }
	        packed += escape(DrawData[i]); //'escape' encodes dataset into unicode
	    }
	} else {
		for (i = 0; (i < DrawData1.length); i++) {
	        if (i > 0) {
	            packed += ","; // each set of data separated by comma
	        }
	        packed += escape(DrawData1[i]); //'escape' encodes dataset into unicode
	    }
	}
    
    document.getElementById(divId + "-SubmitData").value = packed; //stores image data into hidden form field
    
	var tmp = document.getElementById(divId + "-signature").value;
	if (tmp.length > 0) {
		document.getElementById(divId + "-signature").value = escape(tmp);
	}
}
function RecallImage(which){
	var divId = getElementIdPrefix(which);
    if ("SignCanvas" == divId) {
		for (i = 0; (i < TempData.length); i++) {
	        var Parameters = new Array();
	        Parameters = TempData[i].split("|");
	        RedrawImage(which, Parameters);
	    }
	} else {
		for (i = 0; (i < TempData1.length); i++) {
	        var Parameters = new Array();
	        Parameters = TempData1[i].split("|");
	        RedrawImage(which, Parameters);
	    }
	}
	if (document.getElementById(divId + "-signature").value.length > 0) {
		showSignature(which);
	}
}

function DecodeData(which){
    var divId = getElementIdPrefix(which);
    var query = document.getElementById(divId + "-SubmitData").value;
    var data = query.split(',');
    for (i = 0; (i < data.length); i++) {
        data[i] = unescape(data[i]);
    }
	if ("SignCanvas" == divId) {
		TempData = data;
    	DrawData = new Array();
	} else {
		TempData1 = data;
    	DrawData1 = new Array();
	}
    
    document.getElementById(divId + "-DrawData").value = document.getElementById(divId + "-SubmitData").value;
    document.getElementById(divId + "-SubmitData").value = "";
	
	var tmp = document.getElementById(divId + "-signature").value;
	if (tmp.length > 0) {
		document.getElementById(divId + "-signature").value = unescape(tmp);
		//window.console.log(document.getElementById(divId + "-signature").value);
	}
	if (location.href.indexOf("fdid") != -1) {
		document.getElementById(divId + "-signature").style.display="none";
		document.getElementById(divId + "-ClearSignature").style.display="none";
		document.getElementById(divId).onmousedown="";
		document.getElementById(divId).onmouseup="";
		document.getElementById(divId).onmousemove="";
		document.getElementById(divId).onmouseover="";
		document.getElementById(divId).onmouseout="";
	}
}

function ReloadImage(){
	$("div.sig").each(function() {
		DecodeData(this);
    	RecallImage(this);
	})
}
