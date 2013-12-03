var StrokeColor = "black";
var StrokeThickness = 2;
var FreehandSwitch = true;
var DrawTool = "Freehand";

function SetMouseDown(which){
	var index = getSignatureIndex(which);
	MouseDown[index - 1] = true;
}

function SetMouseUp(which){
	var index = getSignatureIndex(which);
	MouseDown[index - 1] = false;
}

function SetDrawOn(which){
	var index = getSignatureIndex(which);
	DrawSwitch[index - 1] = true;
}

function SetDrawOff(which){
	var index = getSignatureIndex(which);
	DrawSwitch[index - 1]  = false;
}

function RoundTo(n, d){
	//rounds n to the nearest d
	var i = Math.round(n/d) * d;
	return i;
}

function SetStart(which){
	var index = getSignatureIndex(which);
	x0[index - 1] = (mousex - cnvLeft[index - 1]);
	y0[index - 1] = (mousey - cnvTop[index - 1]);
}

function GetXY(which, x, y){
	var index = getSignatureIndex(which);
	var t = StrokeThickness;
	var l = Xposition[index - 1].length - 1;	//l = last position
	var h = Math.sqrt(Math.pow((Xposition[index - 1][l] - x),2)+Math.pow((Yposition[index - 1][l] - y),2)) //calc hypotenuse
	if(Xposition[index - 1].length<2){
		Xposition[index - 1].push(x);
		Yposition[index - 1].push(y);
	}
	else {
		if (h>t){
			Xposition[index - 1].push(x);
			Yposition[index - 1].push(y);
		}
	}
}

function ClearXY(which){
	var index = getSignatureIndex(which);
	Xposition[index - 1] = [];
	Yposition[index - 1] = [];
}

function ArrToStr(Arr, s){
	//convert array values to string
	var Str = "";
	for (n = 0; (n < Arr.length); n++)
	 {
		if (n > 0)
		{
			Str += s; // each set of data separated by s
		}
		Str += Arr[n];
	}
 	return Str;
}

function StrToArr(Str,s){
	//converts string to an array
	var Arr  = Str.split(s);
	for (n=0;n<Arr.length;n++){
		Arr[n] = parseInt(Arr[n]);
	}
	return Arr;
}

function AddFreehand(which, canvas,x,y,StrokeColor){
	var index = getSignatureIndex(which);
	var X = Xposition[index - 1];
	var Y = Yposition[index - 1];
	jg[index - 1].setColor(StrokeColor);
	jg[index - 1].setStroke(StrokeThickness);
	if (X.length>1){
		var a = X.length - 2;
		var b = a + 1;
		var x1 = parseInt(X[a]);
		var y1 = parseInt(Y[a]);
		jg[index - 1].drawLine(x1,y1,x,y);
		jg[index - 1].paint();
	}
}

function DrawFreehand(which, canvas,X,Y,StrokeColor){
	var divId = getElementIdPrefix(which);
	var index = getSignatureIndex(which);
	canvas.setColor(StrokeColor);
	canvas.setStroke(StrokeThickness);
	canvas.drawPolyline(X,Y);
	canvas.paint();
	//store parameters in an array
	var StrX = ArrToStr(X,':');
	var StrY = ArrToStr(Y,':');
	var Parameter = "Freehand" + "|" +  StrX + "|" + StrY + "|" + StrokeColor;
	DrawData[index - 1].push(Parameter);
	document.getElementById(divId + "-DrawData").value = DrawData[index - 1];
}

function DrawMarker(which){
	var index = getSignatureIndex(which);
	if(DrawSwitch[index - 1]){
		var x = parseInt(mousex - cnvLeft[index - 1]);
		var y = parseInt(mousey - cnvTop[index - 1]);
		if(FreehandSwitch){
			DrawFreehand(which, jg[index - 1],Xposition[index - 1],Yposition[index - 1],StrokeColor);
			ClearXY(which);
		}
	}
}

function DrawPreview(which){
	var index = getSignatureIndex(which);
	var x = parseInt(mousex-cnvLeft[index - 1]);
	var y = parseInt(mousey-cnvTop[index - 1]);
	if (MouseDown[index - 1]){
		if(FreehandSwitch){
			GetXY(which, x,y);
			AddFreehand(which, pv[index - 1],x,y,StrokeColor);
		}
	}
}

function RedrawImage(which, RedrawParameter){
	var index = getSignatureIndex(which);
	var DrawingType = RedrawParameter[0];
	if(DrawingType == "Freehand"){
		var X = StrToArr(RedrawParameter[1], ':');
		var Y = StrToArr(RedrawParameter[2], ':');
		StrokeColor = RedrawParameter[3];
		DrawFreehand(which, jg[index - 1],X,Y,StrokeColor);
	}
}

function Undo(which){
	var index = getSignatureIndex(which);
	var divId = getElementIdPrefix(which);
	jg[index - 1].clear();
	TempData[index - 1] = DrawData[index - 1];
	DrawData[index - 1] = new Array();
	document.getElementById(divId + "-DrawData").value = "";
	for (i=0; (i < (TempData[index - 1].length - 1) ); i++){
		var Parameters = TempData[index - 1][i].split("|");
		RedrawImage(which,Parameters);
	}
}

function RecallImage(which){
	var index = getSignatureIndex(which);
	for (i=0; (i < TempData[index - 1].length);i++){
		var Parameters = new Array();
		Parameters =  TempData[index - 1][i].split("|");
		RedrawImage(which, Parameters);
	}
}

function Clear(which){
	var index = getSignatureIndex(which);
	var divId = getElementIdPrefix(which);
	jg[index - 1].clear();
	TempData[index - 1] = new Array();
	DrawData[index - 1] = new Array();
	SubmitData[index - 1] = new Array();
	document.getElementById(divId + "-TempData").value = "";
	document.getElementById(divId + "-DrawData").value = "";
	document.getElementById(divId + "-SubmitData").value = "";
	Xposition[index - 1] = new Array();
	Yposition[index - 1] = new Array();
}

function ClearExceptSubmit(which){
	var index = getSignatureIndex(which);
	var divId = getElementIdPrefix(which);
	jg[index - 1].clear();
	TempData[index - 1] = new Array();
	DrawData[index - 1] = new Array();
	document.getElementById(divId + "-TempData").value = "";
	document.getElementById(divId + "-DrawData").value = "";
	Xposition[index - 1] = new Array();
	Yposition[index - 1] = new Array();
}

function SubmitImage(){
	jQuery("div[type='FreehandSignatureBox']").each(function(){
		EncodeData(this);
	});
}

function EncodeData(which){
	var divId = getElementIdPrefix(which);
	var index = getSignatureIndex(which);
	var packed = "";  // Initialize packed or we get the word 'undefined'
	//Converting image data in array into a string
	for (i = 0; (i < DrawData[index - 1].length); i++){
		if (i > 0){
			packed += ","; // each set of data separated by comma
		}
		packed += escape(DrawData[index - 1][i]); 	//'escape' encodes dataset into unicode
	}
	document.getElementById(divId + "-SubmitData").value = packed;  //stores image data into hidden form field
}

function DecodeData(which){
	var index = getSignatureIndex(which);
	var divId = getElementIdPrefix(which);
	var query = document.getElementById(divId + "-SubmitData").value;
	var data = query.split(',');
	for (i = 0; (i < data.length); i++){
		data[i] = unescape(data[i]);
	}
	TempData[index - 1] = data;
	DrawData[index - 1] = new Array();
	document.getElementById(divId + "-DrawData").value = document.getElementById(divId + "-SubmitData").value;
	document.getElementById(divId + "-SubmitData").value = "";
}

function ReloadImage(){
	//DecodeData();
	//RecallImage();
	
	jQuery("div[type='FreehandSignatureBox']").each(function(){
		DecodeData(this);
		RecallImage(this);
	});
}