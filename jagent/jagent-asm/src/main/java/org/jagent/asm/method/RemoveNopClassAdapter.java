package org.jagent.asm.method;

import static org.objectweb.asm.Opcodes.ASM4;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class RemoveNopClassAdapter extends ClassVisitor {
	public RemoveNopClassAdapter(ClassVisitor cv) {
		super(ASM4, cv);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv;
		mv = cv.visitMethod(access, name, desc, signature, exceptions);
		if (mv != null && !name.equals("<init>")) {
			mv = new RemoveNopAdapter(mv);
		}
		return mv;
	}
}