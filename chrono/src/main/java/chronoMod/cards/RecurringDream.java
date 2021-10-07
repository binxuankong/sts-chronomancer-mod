package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.characters.Chronomancer;
import chronoMod.powers.RecurringDreamPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class RecurringDream extends AbstractXCostCard {
    public static final String ID = ChronoMod.makeID(RecurringDream.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int BLOCK = 10;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public RecurringDream() {
        super(ID, IMG, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.getEffectNum(p);
        if (effect > 0) {
            this.addToBot(new ApplyPowerAction(p, p, new RecurringDreamPower(p, effect, this.block)));
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RecurringDream();
    }
}
