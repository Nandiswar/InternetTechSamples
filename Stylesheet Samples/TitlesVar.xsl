<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:p="http://www.w3.org/2005/Atom"
  xmlns="http://www.w3.org/1999/xhtml"
  version="1.0">

  <xsl:output method="xml" 
    omit-xml-declaration="yes"
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"/> 
    <xsl:variable name="entriesVar" select="//p:feed/p:entry"/>
    <xsl:template match="/">
      <html>
        <body>  
          <h1>W3C Atom Document</h1>
          <ul>       
            <xsl:for-each select="$entriesVar">
              <li>
                <xsl:value-of select="./p:title"/>
              </li>
            </xsl:for-each>   
         </ul>  
       </body>
     </html>
   </xsl:template>
</xsl:stylesheet>