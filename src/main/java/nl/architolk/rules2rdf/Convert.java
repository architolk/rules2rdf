package nl.architolk.rules2rdf;

import java.io.FileOutputStream;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.RDFLanguages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topbraid.shacl.rules.RuleUtil;

public class Convert {

  private static final Logger LOG = LoggerFactory.getLogger(Convert.class);

  public static void main(String[] args) {

    if (args.length == 3) {

      LOG.info("Starting conversion");
      LOG.info("Input file: {}",args[0]);
      LOG.info("Rules file: {}",args[1]);
      LOG.info("Ouput file: {}",args[2]);

      try {
        Model inputModel = RDFDataMgr.loadModel(args[0]);
        Model rulesModel = RDFDataMgr.loadModel(args[1]);
        Model resultModel = RuleUtil.executeRules(inputModel,rulesModel,null,null);
        RDFDataMgr.write(new FileOutputStream(args[2]),resultModel, RDFLanguages.filenameToLang(args[2],RDFLanguages.JSONLD));
        LOG.info("Done!");
      }
      catch (Exception e) {
        LOG.error(e.getMessage(),e);
      }
    } else {
      LOG.info("Usage: rules2rdf <input.ttl> <rules.ttl> <output.ttl>");
    }
  }
}
