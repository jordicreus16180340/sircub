/**
 * 
 */
package sircub.classification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.JDOMResult;
import org.jdom2.transform.JDOMSource;
import org.xml.sax.SAXException;

import sircub.AnnotationImporter;
import sircub.Constants;
import sircub.datamodel.AnnotationDataset;
import sircub.datamodel.Instance;
import sircub.datamodel.Label;

/**
 * @author jordi
 *
 */
public class PredictionEnricher {

	public static void enrich(Classification classification) throws IOException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException {
		classification.output("Reading annotation labels file...");
		Map<Integer, Label> labelMap = AnnotationImporter.readLabelsAsIntegerMap(classification.labelsPath);
		
		classification.output("Enriching predictions...");
		classification.instanceMap = enrich2(classification, labelMap);
		
		classification.output("Writing enriched prefictions file...");
		writeEnrichedFile(classification);
		
		classification.output("Writing enriched XML file...");
		writeEnrichedXmlFile(classification);
		
		classification.output("Transforming XML file...");
		transformEnrichedXmlFile(classification);
	}
	
	private static Map<Integer, List<Instance>> enrich2(Classification classification, Map<Integer, Label> labelMap) throws IOException {
		String predictionsPath = null;
		if(classification.experimentTechnique == Constants.SVM)
			predictionsPath = classification.directoryPath + "dataset.txt.predict";
		else if(classification.experimentTechnique == Constants.OPF)
			predictionsPath = classification.directoryPath + "dataset_normalized.dat.out";
		
		FileReader predictionsFileReader = new FileReader(new File(predictionsPath));
		BufferedReader predictionsBufferedReader = new BufferedReader(predictionsFileReader);
		
		String predictionsLine = predictionsBufferedReader.readLine();
		
		Iterator<Map.Entry<Integer, List<Instance>>> cropIterator = classification.instanceMap.entrySet().iterator();
		Map.Entry<Integer, List<Instance>> entry = cropIterator.next();
		Iterator<Instance> instanceIterator = entry.getValue().iterator();
		
		while(predictionsLine != null) {
			int predictionId = Integer.parseInt(predictionsLine);
			
			if(! instanceIterator.hasNext()) {
				entry = cropIterator.next();
				instanceIterator = entry.getValue().iterator();
			}
			Instance instance = instanceIterator.next();
			
			instance.prediction = labelMap.get(predictionId);
			
			predictionsLine = predictionsBufferedReader.readLine();
		}
		
		return classification.instanceMap;
	}
	
	private static void writeEnrichedFile(Classification classification) throws IOException {
		String enrichedPredictionsPath = null;
		if(classification.experimentTechnique == Constants.SVM)
			enrichedPredictionsPath = classification.directoryPath + "dataset.txt.enriched_predict";
		else if(classification.experimentTechnique == Constants.OPF)
			enrichedPredictionsPath = classification.directoryPath + "dataset_normalized.dat.enriched_out";
		
		FileWriter enrichedPredictionsFileWriter = new FileWriter(enrichedPredictionsPath);
		BufferedWriter output = new BufferedWriter(enrichedPredictionsFileWriter);
		
		output.write("prediction_id,");
		output.write("prediction_name,");
		output.write("descriptor_dataset_id,");
		output.write("descriptor_location_id,");
		output.write("latitude,");
		output.write("longitude,");
		output.write("row,");
		output.write("col,");
		output.write("seas,");
		output.write("beg,");
		output.write("end,");
		output.write("beg_relative,");
		output.write("end_relative,");
		output.write("length,");
		output.write("base,");
		output.write("mid_x,");
		output.write("max,");
		output.write("amp,");
		output.write("l_der,");
		output.write("r_der,");
		output.write("l_integ,");
		output.write("s_integ\n");
		
		for(Map.Entry<Integer, List<Instance>> entry : classification.instanceMap.entrySet()) {
			int classId = entry.getKey();
			List<Instance> instances = entry.getValue();
			
			for(Instance instance : instances) {
				output.write(instance.prediction.id + ",");
				output.write(instance.prediction.name + ",");
				output.write(instance.descriptorLocation.descriptorDatasetId + ",");
				output.write(instance.descriptorLocation.id + ",");
				output.write(instance.descriptorLocation.latitude + ",");
				output.write(instance.descriptorLocation.longitude + ",");
				output.write(instance.descriptor.row + ",");
				output.write(instance.descriptor.col + ",");
				output.write(instance.descriptor.seas + ",");
				output.write(instance.descriptor.beg + ",");
				output.write(instance.descriptor.end + ",");
				output.write(instance.descriptor.begRelative + ",");
				output.write(instance.descriptor.endRelative + ",");
				output.write(instance.descriptor.length + ",");
				output.write(instance.descriptor.base + ",");
				output.write(instance.descriptor.midX + ",");
				output.write(instance.descriptor.max + ",");
				output.write(instance.descriptor.amp + ",");
				output.write(instance.descriptor.lDer + ",");
				output.write(instance.descriptor.rDer + ",");
				output.write(instance.descriptor.lInteg + ",");
				output.write(instance.descriptor.sInteg + "\n");
			}
		}
		
		output.close();
	}
	
	private static void writeEnrichedXmlFile(Classification classification) throws IOException {
		Document document = new Document();
		
		Element sircubElement = new Element("sircub");
		document.addContent(sircubElement);
		
		Element instancesElement = new Element("instances");
		sircubElement.addContent(instancesElement);
		
		for(Map.Entry<Integer, List<Instance>> entry : classification.instanceMap.entrySet()) {
			int classId = entry.getKey();
			List<Instance> instances = entry.getValue();
			
			for(Instance instance : instances) {
				Element instanceElement = new Element("instance");
				instancesElement.addContent(instanceElement);
				
				Element predictionElement = new Element("prediction");
				instanceElement.addContent(predictionElement);
				predictionElement.setAttribute("id", "" + instance.prediction.id);
				
				Element nameElement = new Element("name");
				predictionElement.addContent(nameElement);
				nameElement.setText(instance.prediction.name);
				
				Element descriptorLocationElement = new Element("descriptor-location");
				instanceElement.addContent(descriptorLocationElement);
				descriptorLocationElement.setAttribute("descriptor-dataset-id", "" + instance.descriptorLocation.descriptorDatasetId);
				descriptorLocationElement.setAttribute("id", "" + instance.descriptorLocation.id);
				
				Element latitudeElement = new Element("latitude");
				descriptorLocationElement.addContent(latitudeElement);
				latitudeElement.setText("" + instance.descriptorLocation.latitude);
				
				Element longitudeElement = new Element("longitude");
				descriptorLocationElement.addContent(longitudeElement);
				longitudeElement.setText("" + instance.descriptorLocation.longitude);
				
				Element descriptorElement = new Element("descriptor");
				instanceElement.addContent(descriptorElement);
				
				Element rowElement = new Element("row");
				descriptorElement.addContent(rowElement);
				rowElement.setText("" + instance.descriptor.row);
				
				Element colElement = new Element("col");
				descriptorElement.addContent(colElement);
				colElement.setText("" + instance.descriptor.col);
				
				Element seasElement = new Element("seas");
				descriptorElement.addContent(seasElement);
				seasElement.setText("" + instance.descriptor.seas);
				
				Element begElement = new Element("beg");
				descriptorElement.addContent(begElement);
				begElement.setText("" + instance.descriptor.beg);
				
				Element endElement = new Element("end");
				descriptorElement.addContent(endElement);
				endElement.setText("" + instance.descriptor.end);
				
				Element begRelativeElement = new Element("beg-relative");
				descriptorElement.addContent(begRelativeElement);
				begRelativeElement.setText("" + instance.descriptor.begRelative);
				
				Element endRelativeElement = new Element("end-relative");
				descriptorElement.addContent(endRelativeElement);
				endRelativeElement.setText("" + instance.descriptor.endRelative);
				
				Element lengthElement = new Element("length");
				descriptorElement.addContent(lengthElement);
				lengthElement.setText("" + instance.descriptor.length);
				
				Element baseElement = new Element("base");
				descriptorElement.addContent(baseElement);
				baseElement.setText("" + instance.descriptor.base);
				
				Element midXElement = new Element("mid-x");
				descriptorElement.addContent(midXElement);
				midXElement.setText("" + instance.descriptor.midX);
				
				Element maxElement = new Element("max");
				descriptorElement.addContent(maxElement);
				maxElement.setText("" + instance.descriptor.max);
				
				Element ampElement = new Element("amp");
				descriptorElement.addContent(ampElement);
				ampElement.setText("" + instance.descriptor.amp);
				
				Element lDerElement = new Element("l-der");
				descriptorElement.addContent(lDerElement);
				lDerElement.setText("" + instance.descriptor.lDer);
				
				Element rDerElement = new Element("r-der");
				descriptorElement.addContent(rDerElement);
				rDerElement.setText("" + instance.descriptor.rDer);
				
				Element lIntegElement = new Element("l-integ");
				descriptorElement.addContent(lIntegElement);
				lIntegElement.setText("" + instance.descriptor.lInteg);
				
				Element sIntegElement = new Element("s-integ");
				descriptorElement.addContent(sIntegElement);
				sIntegElement.setText("" + instance.descriptor.sInteg);
			}
		}
		
		String enrichedXmlPath = null;
		if(classification.experimentTechnique == Constants.SVM)
			enrichedXmlPath = classification.directoryPath + "dataset.txt.enriched_predict.xml";
		else if(classification.experimentTechnique == Constants.OPF)
			enrichedXmlPath = classification.directoryPath + "dataset_normalized.dat.enriched_out.xml";
		
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		outputter.output(document, new FileWriter(enrichedXmlPath));
	}
	
	private static void transformEnrichedXmlFile(Classification classification) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {
		String filePath = null;
		if(classification.experimentTechnique == Constants.SVM)
			filePath = classification.directoryPath + "dataset.txt.enriched_predict";
		else if(classification.experimentTechnique == Constants.OPF)
			filePath = classification.directoryPath + "dataset_normalized.dat.enriched_out";
		
		// read the XML to a JDOM2 document
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder dombuilder = factory.newDocumentBuilder();
		
		org.w3c.dom.Document w3cDocument = dombuilder.parse(filePath + ".xml");
		DOMBuilder jdomBuilder = new DOMBuilder();
		Document jdomDocument = jdomBuilder.build(w3cDocument);
		
		// create the JDOMSource from JDOM2 document
		JDOMSource source = new JDOMSource(jdomDocument);
		
		// create the transformer
		Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource("xsl/classification_instances.xsl"));
		
		// create the JDOMResult object
		JDOMResult out = new JDOMResult();
		
		// perform the transformation
		transformer.transform(source, out);
		
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		outputter.output(out.getDocument(), new FileWriter(filePath + ".kml"));
	}
}























































