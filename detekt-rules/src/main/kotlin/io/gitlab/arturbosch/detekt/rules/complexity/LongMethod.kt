package io.gitlab.arturbosch.detekt.rules.complexity

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Metric
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.ThresholdRule
import io.gitlab.arturbosch.detekt.api.ThresholdedCodeSmell
import io.gitlab.arturbosch.detekt.rules.asBlockExpression
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtNamedFunction

/**
 * @configuration threshold - maximum lines in a method (default: 20)
 *
 * @active since v1.0.0
 * @author Artur Bosch
 * @author Marvin Ramin
 */
class LongMethod(config: Config = Config.empty,
				 threshold: Int = DEFAULT_ACCEPTED_METHOD_LENGTH) : ThresholdRule(config, threshold) {

	override val issue = Issue("LongMethod",
			Severity.Maintainability,
			"One method should have one responsibility. Long methods tend to handle many things at once. " +
					"Prefer smaller methods to make them easier to understand.")

	override fun visitNamedFunction(function: KtNamedFunction) {
		val body: KtBlockExpression? = function.bodyExpression.asBlockExpression()
		body?.let {
			val size = body.statements.size
			if (size > threshold) report(
					ThresholdedCodeSmell(issue,
							Entity.from(function),
							Metric("SIZE", size, threshold),
							message = ""))
		}
		super.visitNamedFunction(function)
	}

}

private const val DEFAULT_ACCEPTED_METHOD_LENGTH = 20
