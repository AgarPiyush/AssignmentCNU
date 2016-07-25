import understand
# TODO db path. Can be given in config file. Hardcoding for now. 
db = understand.open('../test-db/test_sonic.udb')
methods = db.ents("methods")

for method in methods:
        if( method.name().endswith('run')):								# Mehods end with run. Finding run() methods
                run_class = method.ref("DefineIn")      						# Classes of method run
                if run_class is not None:
                        run_class_ent = run_class.ent()
                        interface_extend = []
                        for eachref in run_class_ent.refs('Couple'): 			                # Ref runnable and threads of class
                                interface_extend.append(eachref.ent().name())
                                if 'Runnable' in interface_extend or 'Thread' in interface_extend:
                                        called_by = method.refs('Java Callby') 	                        # All methods that call run
                                        for eachref in called_by:
                                                entity = eachref.ent()
                                                entityl = (eachref.line(), eachref.column())
                                                if 'Runnable' in interface_extend:
                                                        print('Method: ', method.longname(), 'located in class', run_class.ent().longname(),            
                                                        'which implements Runnable is called by', entity.longname(), 'at (line, col)',entityl, 
                                                        'Did you mean to call start() instead?')
                                                else:
                                                        print('Method: ', method.longname(), 'located in class', run_class.ent().longname(),
                                                        'which extends thread is called by', entity.longname(), 'at (line, col)',entityl,
                                                        'Did you mean to call start() instead?')