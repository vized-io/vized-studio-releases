<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
   <xsl:template match="/">
   <invoice>
     <customer>
       <name>
         <xsl:value-of select="/order/buyer/name"/>
       </name>
     </customer>
     <metadata>
       <version>
         <xsl:text>2.0</xsl:text>
       </version>
     </metadata>
       <xsl:for-each select="/order/products">
         <items>
         <item>
           <sku>
             <xsl:value-of select="product/sku"/>
           </sku>
           <price>
             <xsl:value-of select="product/price"/>
           </price>
         </item>
         </items>
       </xsl:for-each>
   </invoice>
   </xsl:template>
</xsl:stylesheet>