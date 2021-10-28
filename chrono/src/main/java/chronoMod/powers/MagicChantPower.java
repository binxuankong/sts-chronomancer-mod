package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MagicChantPower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("MagicChant");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private AbstractCard card;

    public MagicChantPower(AbstractCreature owner, AbstractCard card) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID;
        this.card = card;
        this.updateDescription();
        this.loadRegion("hymn");
    }

    @Override
    public void recallEffect() {
        // this.addToBot(new MakeTempCardInHandAction(this.card.makeStatEquivalentCopy(), 1));
        this.addToBot(new MakeTempCardInDrawPileAction(this.card.makeStatEquivalentCopy(), 1, true, true));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.card.name + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new MagicChantPower(this.owner, this.card);
    }
}
