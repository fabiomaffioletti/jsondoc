package org.jsondoc.springmvc.controller;

import java.util.LinkedList;
import java.util.List;

import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.springmvc.util.SpringMvcDocUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/jsondoc")
public class JSONDocController
{
    
    private JSONDoc cachedDoc;
    
	private String version;
	private String basePath;
	private boolean cacheResult;
	private List<String> packages;
	
	
	public JSONDocController()
	{
	    super();
	    
	    this.version = "";
	    this.basePath = "";
	    this.packages = null;
	    this.cachedDoc = null;
	    this.cacheResult = false;
	    
	}
	
	
	/* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
	
	
	@RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JSONDoc getApi()
	{
	    
	    if( cacheResult )
	    {
	        if( cachedDoc == null )
	            cachedDoc = buildApiDoc();
	        
	        return cachedDoc;
	    }
	        
        return buildApiDoc();
        
    }
	
	
	/* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
	
	
	/**
	 * Checks the parameters and builds the {@link JSONDoc} object.
	 */
	private JSONDoc buildApiDoc()
	{
	    
	    if( packages != null )
	        return SpringMvcDocUtils.getApiDoc( version, basePath, packages );
	    else
	        return SpringMvcDocUtils.getApiDoc( version, basePath, new LinkedList<String>() );
	    
	}
	
	
	/* ******************* */
	/*  GETTERS & SETTERS  */
	/* ******************* */

	
	public void setVersion( String version )
	{
		this.version = version;
	}

	public void setBasePath( String basePath )
	{
		this.basePath = basePath;
	}

	public void setPackages( List<String> packages )
	{
		this.packages = packages;
	}
    
    public void setCacheResult( boolean cacheResult )
    {
        this.cacheResult = cacheResult;
    }
	
}