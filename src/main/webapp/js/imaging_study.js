function imagingStudyLoaded(cnt)
{
	ZeroClipboard.setMoviePath( '../js/ZeroClipboard.swf' );
	
	//alert("in imagingStudyLoaded");
	for(i=0;i<=cnt;i++)
	{
		try{
		var clip = new ZeroClipboard.Client();
		//clip.setText("111"); 
		clip.setHandCursor( true );     
		//clip.addEventListener('load', my_load);    
		clip.addEventListener('mouseDown', fn_imaging_studies_mouse_over);
		clip.addEventListener('complete', fn_imaging_studies_complete);    
		
		clip.glue('div_imaging_study_copy_'+i, 'con_div_imaging_study_copy_'+i);
		}
		catch(e){}
		//alert("init done.. i = "+i);
	}
	
}

/*function my_load(client)
{
	alert("in my_load");
}*/

function fn_imaging_studies_complete(client, text)
{
	//alert("Study details has been copied to clipboard");
}

function fn_imaging_studies_mouse_over(client)
{
	//alert("in my_mouse_over.. client = "+client);
	var str = client.domElement.nextSibling.innerHTML;
	//alert("str = "+str);
	client.setText(str);
	//alert("text set");
	
}
