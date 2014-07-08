package org.jagent.asm.generate;

import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.V1_5;

import org.objectweb.asm.ClassVisitor;

public class ChangeVersionAdapter extends ClassVisitor {
	public ChangeVersionAdapter(ClassVisitor cv) {
		super(ASM4, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		cv.visit(V1_5, access, name, signature, superName, interfaces);
	}
}