package pluginmetamap; 
 
import gate.*; 
import gate.annotation.AnnotationImpl;
import gate.creole.*; 
import gate.creole.metadata.*; 
import java.net.URL;
import java.util.Iterator;
 
/** 
 * Processing Resource.  The @CreoleResource annotation marks this 
 *  class as a GATE Resource, and gives the information GATE needs 
 *  to configure the resource appropriately. 
 */ 
@CreoleResource(name = "Example PR", 
                comment = "An example processing resource") 
public class NewPlugin extends AbstractLanguageAnalyser { 
 
   /* 
    * this method gets called whenever an object of this 
    * class is created either from GATE Developer GUI or if 
    * initiated using Factory.createResource() method. 
    */ 
   public Resource init() throws ResourceInstantiationException { 
        // here initialize all required variables, and may 
        // be throw an exception if the value for any of the 
        // mandatory parameters is not provided 
 
        if(this.rulesURL == null) 
            throw new ResourceInstantiationException("rules URL null"); 
 
        return this; 
   } 
 
 
   /* 
    * this method should provide the actual functionality of the PR 
    * (from where the main execution begins). This method 
    * gets called when user click on the "RUN" button in the 
    * GATE Developer GUI’s application window. 
    */ 
   public void execute() throws ExecutionException { 
        // write code here 
       System.out.println("Iniciado");
       /*String contenido=this.document.getContent().toString();
       String[] palabras=contenido.split(" ");
       for(String palabra:palabras){
           String palabraReplaced = palabra.replaceAll(",", "").replaceAll("\\.", "")
                   .replaceAll(";", "").replaceAll(":", "");
           System.out.println(palabraReplaced);
       }*/
       try {
            AnnotationSet set=this.document.getAnnotations();
            AnnotationSet set1=set.get("Token");
            Iterator<Annotation> iterator = set1.iterator();
            AnnotationSet out=this.document.getAnnotations("miPlugin");

            while(iterator.hasNext()){
                Annotation an=iterator.next();
                System.out.println(an.getFeatures().get("string").toString());

                out.add(an.getStartNode().getOffset(),an.getEndNode().getOffset()
                        ,"Mi etiqueta",Utils.featureMap("type","cosa"));
            }
       } catch (Exception e) {
           System.err.println(e.getMessage());
           e.printStackTrace(System.err);
       }
       
       System.out.println();
   } 
 
   /* this method is called to reinitialize the resource */ 
   public void reInit() throws ResourceInstantiationException { 
       // reinitialization code 
       System.out.println("Reiniciado");
   } 
 
   /* 
    * There are two types of parameters 
    * 1. Init time parameters − values for these parameters need to be 
    * provided at the time of initializing a new resource and these 
    * values are not supposed to be changed. 
    * 2. Runtime parameters − values for these parameters are provided 
    * at the time of executing the PR. These are runtime parameters and 
    * can be changed before starting the execution 
    * (i.e. before you click on the "RUN" button in GATE Developer) 
    * A parameter myParam is specified by a pair of methods getMyParam 
    * and setMyParam (with the first letter of the parameter name 
    * capitalized in the normal Java Beans style), with the setter 
    * annotated with a @CreoleParameter annotation. 
    * 
    * for example to set a value for outputAnnotationSetName 
    */ 
   String outputAnnotationSetName; 
 
   //getter and setter methods 

   /* get<parameter name with first letter Capital>  */ 
   public String getOutputAnnotationSetName() { 
        return outputAnnotationSetName; 
   } 
 
   /* The setter method is annotated to tell GATE that it defines an 
    * optional runtime parameter. 
    */ 
   @Optional 
   @RunTime 
   @CreoleParameter( 
       comment = "name of the annotationSet used for output") 
   public void setOutputAnnotationSetName(String setName) { 
        this.outputAnnotationSetName = setName; 
   } 
 
   /** Init−time parameter */ 
   URL rulesURL; 
 
   // getter and setter methods 
   public URL getRulesURL() { 
      return rulesURL; 
   } 
 
   /* This parameter is not annotated @RunTime or @Optional, so it is a 
    * required init−time parameter. 
    */ 
   @CreoleParameter( 
       comment = "example of an inittime parameter", 
       defaultValue = "resources/morph/default.rul") 
   public void setRulesURL(URL rulesURL) { 
      this.rulesURL = rulesURL; 
   } 
}