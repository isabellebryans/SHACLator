package org.example;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.topbraid.shacl.validation.ValidationUtil;
import org.topbraid.shacl.vocabulary.SH;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ShaclMain {

    public static void main(String[] args) throws FileNotFoundException {
        // load data
        Model dataGraph = SwissKnife.initAndLoadModelFromResource("movie-owl.ttl", Lang.TURTLE);

        // load constraints
        Model shapesGraph = SwissKnife.initAndLoadModelFromResource("movie-constraints.ttl", Lang.TURTLE);

        // validation
        Resource validationResult = ValidationUtil.validateModel(dataGraph, shapesGraph, false);

        // write validation report
        Model resultModel = validationResult.getModel();
        resultModel.setNsPrefix(SH.PREFIX, SH.NS);
        RDFDataMgr.write(new FileOutputStream("report.ttl"), resultModel, Lang.TURTLE);
    }
}
