use(function () {
	var isDAMPath = org.apache.commons.lang3.StringUtils.contains(this.path, "/content/dam/");
	var isInternal = org.apache.commons.lang3.StringUtils.startsWith(this.path, "/content/");
	var convertedPath = '';
	if((!isDAMPath) && isInternal) {
		convertedPath = this.path + '.html';
	} else {
		convertedPath = this.path;
	}
	
return {
	convertedPath: convertedPath
    };
});