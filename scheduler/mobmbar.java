//public class MOBMBARHandoff extends MobilityModel {
//    // Bipartite graph of nodes and modules
//    private BiMap<Node, Set<Module>> nodeModules;  
//    private BiMap<Module, Set<Node>> moduleNodes;
//    
//    public MOBMBARHandoff() {
//        nodeModules = HashBiMap.create();
//        moduleNodes = nodeModules.inverse();
//    }
//    
//    @Override
//    public void addNode(Node node) {
//        // Add node to bipartite graph
//        nodeModules.put(node, new HashSet<>());
//    }
//    
//    @Override
//    public void addModule(Module module) {
//        // Add module to bipartite graph
//        moduleNodes.put(module, new HashSet<>());
//    }  
//    
//    @Override
//    public void mapModuleToNode(Module module, Node node) {
//        // Add mapping to bipartite graph
//        Set<Module> modules = nodeModules.get(node);
//        modules.add(module);
//        nodeModules.put(node, modules); 
//        
//        Set<Node> nodes = moduleNodes.get(module);
//        nodes.add(node);
//        moduleNodes.put(module, nodes);
//    }
//    
//    @Override
//    public Node handleHandoff(Node currentNode, List<Node> availableNodes) { 
//        // Get current location of mobile device
//        Location currentLocation = getCurrentLocation();  
//        
//        // Find closest node and farthest module from current location
//        Node closestNode = null;
//        double minDistance = Double.MAX_VALUE;
//        Module farthestModule = null;
//        double maxDistance = 0;
//        
//        for (Node node : availableNodes) {
//            double distance = currentLocation.distance(node.getLocation());
//            if (distance < minDistance) {
//                closestNode = node;
//                minDistance = distance;
//            }
//            
//            // Get modules mapped to this node
//            Set<Module> modules = nodeModules.get(node);
//            for (Module module : modules) {
//                double moduleDistance = currentLocation.distance(module.getLocation());
//                if (moduleDistance > maxDistance) {
//                    farthestModule = module;
//                    maxDistance = moduleDistance; 
//                }
//            }
//        }
//        
//        // Check if handoff will reduce distance to farthest module
//        double distanceBefore = currentLocation.distance(farthestModule.getLocation()); 
//        double distanceAfter = closestNode.getLocation().distance(farthestModule.getLocation());
//        if (distanceAfter < distanceBefore) {
//            System.out.println("Performing handoff from " + currentNode + " to " + closestNode);
//            return closestNode;
//        }
//        
//        // If no module reduction, return current node 
//        return currentNode;
//    }
//}