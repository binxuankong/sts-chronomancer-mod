package chronoMod.cards;

import chronoMod.powers.JadePower;
import chronoMod.powers.SpellBoostPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public abstract class AbstractXCostCard extends AbstractDynamicCard {
    private static final int COST = -1;

    public AbstractXCostCard(final String id, final String img, final CardType type, final CardColor color,
                             final CardRarity rarity, final CardTarget target) {
        super(id, img, COST, type, color, rarity, target);
    }

    public int getEffectNum(AbstractPlayer p) {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        // Chemical X
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        // Spell Boost
        AbstractPower spellBoost = p.getPower(SpellBoostPower.POWER_ID);
        if (spellBoost != null) {
            spellBoost.flash();
            effect += spellBoost.amount;
            this.addToBot(new RemoveSpecificPowerAction(p, p, spellBoost));
        }
        // Use energy
        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
        return effect;
    }
}
