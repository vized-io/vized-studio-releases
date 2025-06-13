<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />
  <xsl:template match="/">
    <invoice>
      <customer>
        <name>
          <xsl:value-of select="/order/buyer/name" />
        </name>
      </customer>
      <items>
        <xsl:for-each select="/order/products/product">
          <item>
            <sku>
              <xsl:value-of select="sku" />
            </sku>
            <price>
              <xsl:value-of select="price" />
            </price>
          </item>
        </xsl:for-each>
      </items>
    </invoice>
  </xsl:template>
</xsl:stylesheet>