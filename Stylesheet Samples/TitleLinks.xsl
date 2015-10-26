<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:p="http://www.w3.org/2005/Atom"
  xmlns="http://www.w3.org/1999/xhtml"
  version="1.0">

  <xsl:output method="xml" 
    omit-xml-declaration="yes"
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"/> 

    <xsl:template match="/">
      <html>
        <body>  
          <h1>Titles (with links)</h1>
          <ul>       
           <xsl:for-each select="p:feed/p:entry">
           <li>
            <a href="{p:link/@href}">
              <xsl:value-of select="p:title"/>
            </a>
          </li>
           </xsl:for-each>      
         </ul>  
       </body>
     </html>
   </xsl:template>
</xsl:stylesheet>