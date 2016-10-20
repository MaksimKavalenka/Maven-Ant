<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:html-metadata-functions="http://com.epam.training/csn/html-metadata-functions">

	<xsl:function name="html-metadata-functions:get-last-part-of-uri">
		<xsl:param name="url"/>
		<xsl:value-of select="tokenize($url, '/')[last()]"/>
	</xsl:function>

</xsl:stylesheet>