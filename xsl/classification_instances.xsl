<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<kml xmlns="http://www.opengis.net/kml/2.2">
		<Document>
			<xsl:for-each select="sircub/instances/instance">
				<Placemark>
					<name><xsl:value-of select="descriptor-location/@id"/></name>
					<description>
						<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
							<b>prediction</b><br />
							<xsl:text>id: </xsl:text><xsl:value-of select="prediction/@id"/><br />
							<xsl:text>name: </xsl:text><xsl:value-of select="prediction/name"/><br />
							<b>descriptor_location</b><br />
							<xsl:text>descriptor_dataset_id: </xsl:text><xsl:value-of select="descriptor-location/@descriptor-dataset-id"/><br />
							<xsl:text>id: </xsl:text><xsl:value-of select="descriptor-location/@id"/><br />
							<xsl:text>latitude: </xsl:text><xsl:value-of select="descriptor-location/latitude"/><br />
							<xsl:text>longitude: </xsl:text><xsl:value-of select="descriptor-location/longitude"/>
						<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
					</description>
					<Point>
						<coordinates><xsl:value-of select="descriptor-location/longitude"/>,<xsl:value-of select="descriptor-location/latitude"/>,0</coordinates>
					</Point>
				</Placemark>
			</xsl:for-each>
		</Document>
	</kml>
</xsl:template>

</xsl:stylesheet>





















