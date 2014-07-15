package org.jagent.asm.method;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;

public class RemoveNopAdapter extends MethodVisitor {

	public RemoveNopAdapter(MethodVisitor mv) {
		super(ASM4, mv);
	}

	@Override
	public void visitInsn(int opcode) {
		if (opcode != NOP) {
			mv.visitInsn(opcode);
		}
	}
}
