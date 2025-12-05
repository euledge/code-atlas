package com.euledge.codeatlas.generator;

import com.euledge.codeatlas.model.ClassNode;
import java.util.Map;

public class DrawIoGenerator implements DiagramGenerator {
    @Override
    public String generate(Map<String, ClassNode> classes) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<mxfile host=\"Electron\" modified=\"2023-10-27T00:00:00.000Z\" agent=\"5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) draw.io/21.6.8 Chrome/114.0.5735.289 Electron/25.5.0 Safari/537.36\" etag=\"123456789\" version=\"21.6.8\" type=\"device\">\n");
        sb.append("  <diagram id=\"C5RBs43oDa-KdzZeNtuy\" name=\"Page-1\">\n");
        sb.append(
                "    <mxGraphModel dx=\"1422\" dy=\"762\" grid=\"1\" gridSize=\"10\" guides=\"1\" tooltips=\"1\" connect=\"1\" arrows=\"1\" fold=\"1\" page=\"1\" pageScale=\"1\" pageWidth=\"827\" pageHeight=\"1169\" math=\"0\" shadow=\"0\">\n");
        sb.append("      <root>\n");
        sb.append("        <mxCell id=\"0\" />\n");
        sb.append("        <mxCell id=\"1\" parent=\"0\" />\n");

        int x = 20;
        int y = 20;
        int idCounter = 2;

        for (ClassNode node : classes.values()) {
            String id = "class_" + node.getName();
            sb.append(String.format(
                    "        <mxCell id=\"%s\" value=\"%s\" style=\"swimlane;fontStyle=1;childLayout=stackLayout;horizontal=1;startSize=26;horizontalStack=0;resizeParent=1;parent=1;\" vertex=\"1\">\n",
                    id, node.getName()));
            sb.append(String.format(
                    "          <mxGeometry x=\"%d\" y=\"%d\" width=\"160\" height=\"50\" as=\"geometry\" />\n", x, y));
            sb.append("        </mxCell>\n");

            x += 180;
            if (x > 800) {
                x = 20;
                y += 100;
            }
        }

        // Edges
        for (ClassNode node : classes.values()) {
            String sourceId = "class_" + node.getName();

            if (node.getSuperClassName() != null && classes.containsKey(node.getSuperClassName())) {
                String targetId = "class_" + node.getSuperClassName();
                sb.append(createEdge(sourceId, targetId, "endArrow=block;endSize=16;endFill=0;html=1;"));
            }

            for (String iface : node.getInterfaces()) {
                if (classes.containsKey(iface)) {
                    String targetId = "class_" + iface;
                    sb.append(createEdge(sourceId, targetId, "endArrow=block;endSize=16;endFill=0;html=1;dashed=1;"));
                }
            }

            for (String dep : node.getDependencies()) {
                if (classes.containsKey(dep)) {
                    String targetId = "class_" + dep;
                    sb.append(createEdge(sourceId, targetId, "endArrow=open;endSize=12;dashed=1;html=1;"));
                }
            }
        }

        sb.append("      </root>\n");
        sb.append("    </mxGraphModel>\n");
        sb.append("  </diagram>\n");
        sb.append("</mxfile>");
        return sb.toString();
    }

    private String createEdge(String source, String target, String style) {
        return String.format(
                "        <mxCell id=\"edge_%s_%s\" style=\"%s\" edge=\"1\" parent=\"1\" source=\"%s\" target=\"%s\">\n"
                        +
                        "          <mxGeometry relative=\"1\" as=\"geometry\" />\n" +
                        "        </mxCell>\n",
                source, target, style, source, target);
    }
}
