<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xhtml="http://www.w3.org/1999/xhtml"
                xmlns:html-metadata-functions="http://com.epam.training/csn/html-metadata-functions">

	<xsl:import href="html-metadata-functions.xsl"/>

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="xhtml:article/@id">
		<xsl:attribute name="id">
			<xsl:value-of select="translate(., '-', '')"/>
		</xsl:attribute>
	</xsl:template>

	<xsl:template match="xhtml:article/@about ">
		<xsl:variable name="url" select="."/>
		<xsl:attribute name="about">
			<xsl:value-of select="concat(substring-before($url, html-metadata-functions:get-last-part-of-uri($url)), translate(substring-after($url, '#'), '-', ''))"/>
		</xsl:attribute>
	</xsl:template>

	<xsl:template match="xhtml:span[@rel='dcterms:hasVersion']/@resource">
		<xsl:variable name="url" select="."/>
		<xsl:attribute name="resource">
			<xsl:value-of select="concat(substring-before($url, html-metadata-functions:get-last-part-of-uri($url)), substring-after($url, '#'))"/>
		</xsl:attribute>
	</xsl:template>

	<xsl:template match="xhtml:meta[@charset='UTF-8']"/>

</xsl:stylesheet>