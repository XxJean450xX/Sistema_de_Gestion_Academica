/**
 * 
 */
/**
 * 
 */
module Sistema_Gestion_Academica {
	requires java.desktop;
    requires java.logging; 

    // These are the new requirements for PDFBox 3.x:
    requires org.apache.pdfbox;
    requires org.apache.fontbox;
    requires org.apache.pdfbox.io; // Crucial for the IOUtils error!

    // These are for the logging system used by PDFBox 3.x:
    requires org.slf4j;
    requires org.slf4j.simple;
    
    opens GUI to java.desktop;
}