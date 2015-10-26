<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : XSLTransformerCode.xsl
    Created on : 3 October, 2015, 5:52 PM
    Author     : nandi_000
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"
                omit-xml-declaration="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <h3><xsl:value-of select="rss/channel/title"/></h3>
        <ul>       
            <xsl:for-each select="rss/channel/item">
                <li>
                    <a href="{link}">
                        <xsl:value-of select="title"/>
                    </a>
                </li>
            </xsl:for-each>      
        </ul>  
       
    </xsl:template>

</xsl:stylesheet>