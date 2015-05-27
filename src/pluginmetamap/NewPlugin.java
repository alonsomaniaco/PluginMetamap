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
/**
 * ----------------------Traducción---------------------------------
 * Recurso de proceso. La anotación @CreoleResource define esta clase 
 * como un Recurso de GATE, y y brinda la información que GATE necesita
 * para configurar el recurso apropiadamente
 */
@CreoleResource(name = "Example PR", 
                comment = "An example processing resource") 
public class NewPlugin extends AbstractLanguageAnalyser { 
 
   /* 
    * this method gets called whenever an object of this 
    * class is created either from GATE Developer GUI or if 
    * initiated using Factory.createResource() method. 
    */ 
    /**
     * ----------------------Traducción---------------------------------
     * Este método es llamado cada vez que un objeto de esta
     * clase es creado ya sea desde GATE Developer GUI o si 
     * se inicializa utilizando el método Factory.createResource().
     */
   public Resource init() throws ResourceInstantiationException { 
        // here initialize all required variables, and may 
        // be throw an exception if the value for any of the 
        // mandatory parameters is not provided 
       /**
        * Aquí se inicializa todas las variables neceesarias, y 
        * puede lanzar una excepcion si el valor para alguno 
        * de los parámetros requeridos no ha sido proporcionado.
        */
 
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
   
   /**
    * ----------------------Traducción---------------------------------
    * ESte método debería proveer la funcionalidad actual del PR(desde
    * donde comienza la ejecución principal). Este método es llamado
    * cuando un usuario hace click en el botón 'RUN' de la ventana de
    * GATE Developer.
    * 
    */
   public void execute() throws ExecutionException { 
        // write code here 
       System.out.println("Iniciado");
       
       /**
        * Código para procesar el texto por nuestra propia cuenta.
        */
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
   //Este método se llama al reiniciar el recurso
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
   /**
    * Hay dos tipos de parametros:
    * 1. Parámetros de inicialización - los valores para estos parametros
    * son obligatorios en la inicializacion de un nuevo recurso y no pueden
    * ser modificados.
    * 2. Parámetros en tiempo de ejecución - el valor de estos parámetros se 
    * obtiene en tiempo de ejecución. Estos son los parámetros de tiempo de
    * ejecución y su valor puede ser modificado antes de comenzar el PR.
    * (antes de hacer click en el botón "RUN" de GATE Developer)
    * Un parámetro MyParam se define por un par de metodos getMyParam y
    * setMyParam(con la primera letra del nombre del parámetro en mayuscula
    * al estilo normal de Java Beans), con el setter anotado con la 
    * anotación @CreoleParameter.
    */
   String outputAnnotationSetName; 
 
   //getter and setter methods 
   //Métodos getter y setter

   /* get<parameter name with first letter Capital>  */ 
   public String getOutputAnnotationSetName() { 
        return outputAnnotationSetName; 
   } 
 
   /* The setter method is annotated to tell GATE that it defines an 
    * optional runtime parameter. 
    */ 
   /**
    * 
    * El método setter se anota para decirle a 
    * GATE que se define un parámetro opcional de tiempo de ejecución.
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
   /**
    * 
    * Este parámetro no está anotado con @Runtime u @Optional, por 
    * lo que se trata de un parámetro de inicio
    */
   @CreoleParameter( 
       comment = "example of an inittime parameter", 
       defaultValue = "resources/morph/default.rul") 
   public void setRulesURL(URL rulesURL) { 
      this.rulesURL = rulesURL; 
   } 
}