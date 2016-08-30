<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<html>
		<body>
			<div align="center">
			
			<h2>Results for <xsl:value-of select="sircub/experiment/id"/></h2>
			
			<table border="1">
				<tr>
					<th style="visibility: hidden"></th>
					<th>
						<xsl:attribute name="colspan">
							<xsl:value-of select="count(sircub/results/rounds/round[1]/classes/class) + 1"/>
						</xsl:attribute>
						<xsl:text>Accuracy (%)</xsl:text>
					</th>
				</tr>
				<tr>
					<th style="visibility: hidden"></th>
					<xsl:for-each select="sircub/results/rounds/round[1]/classes/class">
						<th style="width: 100px">Class <xsl:value-of select="@id"/><br/>(<i><xsl:value-of select="@name"/></i>)</th>
					</xsl:for-each>
					<th style="width: 100px">Mean</th>
				</tr>
				
				<xsl:for-each select="sircub/results/rounds/round">
					<tr>
						<td>Round <xsl:value-of select="@id"/></td>
						<xsl:for-each select="classes/class">
							<td style="text-align: center">
								<xsl:attribute name="title">
									<xsl:value-of select="accuracy/value"/>
								</xsl:attribute>
								<xsl:value-of select="accuracy/percentage"/>
							</td>
						</xsl:for-each>
						<td style="text-align: center">
							<xsl:attribute name="title">
								<xsl:value-of select="mean/accuracy/value"/>
							</xsl:attribute>
							<xsl:value-of select="mean/accuracy/percentage"/>
						</td>
					</tr>
				</xsl:for-each>
				
				<tr>
					<th>Mean</th>
					<xsl:for-each select="sircub/results/mean/classes/class">
						<td style="text-align: center">
							<xsl:attribute name="title">
								<xsl:value-of select="accuracy/value"/>
							</xsl:attribute>
							<xsl:value-of select="accuracy/percentage"/>
						</td>
					</xsl:for-each>
					<td style="text-align: center">
						<xsl:attribute name="title">
							<xsl:value-of select="sircub/results/mean/mean/accuracy/value"/>
						</xsl:attribute>
						<xsl:value-of select="sircub/results/mean/mean/accuracy/percentage"/>
					</td>
				</tr>
				<tr>
					<th>Std deviation</th>
					<xsl:for-each select="sircub/results/mean/classes/class">
						<td style="text-align: center">
							<xsl:attribute name="title">
								<xsl:value-of select="standard-deviation/value"/>
							</xsl:attribute>
							<xsl:value-of select="standard-deviation/percentage"/>
						</td>
					</xsl:for-each>
					<td style="text-align: center">
						<xsl:attribute name="title">
							<xsl:value-of select="sircub/results/mean/mean/standard-deviation/value"/>
						</xsl:attribute>
						<xsl:value-of select="sircub/results/mean/mean/standard-deviation/percentage"/>
					</td>
				</tr>
			</table>
			
			</div>
		</body>
	</html>
</xsl:template>

</xsl:stylesheet>





















