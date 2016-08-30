/**
 * 
 */
package sircub.datamodel;

/**
 * @author jordi
 *
 */
public class Instance {

	public Annotation annotation;
	public DescriptorLocation descriptorLocation;
	public Descriptor descriptor;
	public Label prediction;
	
	public Instance(Annotation annotation, DescriptorLocation descriptorLocation, Descriptor descriptor, Label prediction) {
		this.annotation = annotation;
		this.descriptorLocation = descriptorLocation;
		this.descriptor = descriptor;
		this.prediction = prediction;
	}
}
























































