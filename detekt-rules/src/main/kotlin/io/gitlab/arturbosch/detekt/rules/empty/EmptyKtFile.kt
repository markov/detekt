package io.gitlab.arturbosch.detekt.rules.empty

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Entity
import org.jetbrains.kotlin.psi.KtFile

/**
 * @active since v1.0.0
 * @author schalkms
 * @author Marvin Ramin
 */
class EmptyKtFile(config: Config) : EmptyRule(config) {

	override fun visitKtFile(file: KtFile) {
		if (file.text.isNullOrBlank()) {
			report(CodeSmell(issue, Entity.from(file), message = ""))
		}
	}
}
