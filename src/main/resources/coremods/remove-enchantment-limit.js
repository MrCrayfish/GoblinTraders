function initializeCoreMod() {
	return {
		'edit_block_event': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.inventory.container.RepairContainer',
                'methodName': 'func_82848_d',
                'methodDesc': '()V'
            },
            'transformer': function(method) {
                log("Patching RepairContainer...");
                patch_RepairContainer_updateRepairOutput(method);
                return method;
            }
        }
	};
}

var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');

function patch_RepairContainer_updateRepairOutput(method) {
    var instructions = method.instructions.toArray();
    for (var i = 0; i < instructions.length; i++) {
        var node = instructions[i];
        if(node.getOpcode() == Opcodes.INVOKEVIRTUAL && node.name.equals(ASMAPI.mapMethod("func_77325_b"))) {
            if(node.getNext() !== null && node.getNext().getOpcode() == Opcodes.ISTORE) {
                foundNode = node;
                break;
            }
        }
    }
    if(foundNode !== null) {
        method.instructions.remove(foundNode.getPrevious());
        method.instructions.remove(foundNode.getNext());
        method.instructions.remove(foundNode);
        log("Successfully patched RepairContainer");
    }
}

function log(s) {
    print("[Goblin Traders] " + s);
}